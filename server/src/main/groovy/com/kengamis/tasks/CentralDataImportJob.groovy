package com.kengamis.tasks

import com.kengamis.AppHolder
import com.kengamis.CentralService
import com.kengamis.ChoiceOption
import com.kengamis.Form
import com.kengamis.FormSetting
import com.kengamis.RestHelper
import com.kengamis.Study
import com.kengamis.User
import com.kengamis.Util
import com.omnitech.odkodata2sql.DataInserter
import com.omnitech.odkodata2sql.OdkOdataSlurper
import com.omnitech.odkodata2sql.SqlSchemaGen
import com.omnitech.odkodata2sql.model.OdkFormSource
import com.omnitech.odkodata2sql.model.OdkTable
import grails.gorm.transactions.Transactional
import groovy.json.JsonOutput
import groovy.util.logging.Log4j
import groovy.xml.XmlUtil
import org.openxdata.markup.Converter
import org.openxdata.markup.IFormElement
import org.openxdata.markup.ISelectionQuestion
import org.openxdata.markup.MultiSelectQuestion
import org.openxdata.markup.RepeatQuestion
import org.openxdata.markup.XformType
import org.openxdata.markup.Form as markUpForm

import static com.kengamis.RestHelper.*
import static com.kengamis.RestHelper.withCentral

@Log4j
class CentralDataImportJob extends Script {

    CentralService centralService

    @Override
    Object run() {
        try {
            centralService = AppHolder.bean('centralService')
            def token = centralService.get()
            def study = Study.findByOxdId('7')
            syncCentralData(study, token)
        }
        catch (Exception e) {
            println("Exception: " + e.getMessage())
        }
    }

    static def syncCentralData(Study study, def token) {
        try {
            log.info("===========SYNCING STUDY [$study.name]")
            def forms = withCentral { listForms(study.oxdId, token) }
            for (form in forms) {
                try {
                    Form.withNewSession {
                        syncFormSetting(study, form, token)
                        syncFormData(study, form, token)
                    }

                }
                catch (Exception ex) {
                    log.error("ERROR:::: Failed to synchronise [${form.name}] !!!", Util.sanitize(ex))
                }
            }
        }
        catch (Exception ex) {
            log.error("Failed to Synchronise Form, [$study]", ex)
        }
        log.info("=============Finished syncing data For Study [$study.name]========\n\n")
    }


    static def syncFormSetting(study, def form, token) {
        def tableName = deriveCentralFormName(form)
        def odkTableName = new OdkTable(name: tableName)
        def postFix = deriveCentralFormPostFix(form.name)
        def finalTableName = SqlSchemaGen.createTableName(odkTableName, postFix)
        log.info("  -> Derived table for form [$study.name -> $form.xmlFormId] = [[$finalTableName]]")

        def misForm = Form.findByName(finalTableName) ?: new Form(name: finalTableName, study: study,
                oxdId: form.xmlFormId, displayName: form.name)
        Form.withNewTransaction { misForm.save(failOnError: true, flush: true) }
        log.info("  -> Done Saving form[$form]")

        def versions = withCentral { getFormVersions(study.oxdId, form.xmlFormId, token) }
        for (version in versions) {
            log.info(version)
            importVersion(study, misForm, '___', token)
        }
        log.info("  -> Done Importing form[$form] Settings...")
    }

    static def syncFormData(Study study, def form, token) {
        log.info("======= Syncing Form Data [$study.name -> $form.name] ===========================")
        def misForm = Form.findByOxdId(form.xmlFormId)
        if (misForm != null && misForm.syncMode) {
            def postFix = deriveCentralFormPostFix(form.name)
            OdkFormSource formSource = new OdkOdataSlurper(getCentralRestClient(), token)
                    .projectId(study.oxdId)
                    .formId(form.xmlFormId)
                    .mapToModel()
            def data = withCentral { getDataDocument(token,study.oxdId,form.xmlFormId,'Submissions',0,10000,true) }
            def records = data.value
            records.each { record ->
                def central_system_info = record['__system']
                def central_user_name = central_system_info['submitterName']
                AppHolder.withMisSqlNonTx {
                    new DataInserter(connection, JsonOutput.toJson(record), formSource,true)
                            .setConfig([
                                    'central_user_name': central_user_name,
                            ])
                            .setCoreTablePostFix(postFix)
                            .insert()
                }
            }
        }
        else {
            log.info(" ======= The sync mode of form (${form.name}) is not enabled =======")
        }
    }

    static def importVersion(Study study, Form misForm, def versionName, def token) {
        log.info("        ->Importing version settings [$misForm]-[$versionName]")
        def hasViewableColumns = misForm.hasViewableColumns()
        String formVersionXml = getXform(study.oxdId, misForm.oxdId, token)
        def odkForm = Converter.odk2Form(formVersionXml)
        def flatten = isFlattenForm(odkForm)
        def allFirstLevelQns = flatten ? odkForm.allFirstLevelQuestionsNotInRepeat : odkForm.allFirstLevelQuestions
        def firstLevelQuestions = allFirstLevelQns.findAll { it.xformType != XformType.REPEAT }

        def allTheQuestions = (flatten ? odkForm.allElementsWithIds.findAll { it.xformType != XformType.GROUP } :
                odkForm.allElementsWithIds) as List<IFormElement>

        allTheQuestions.each { IFormElement qn ->
            def idx = 1000
            try {
                idx = allTheQuestions.indexOf(qn)
            } catch (Exception ex) {

            }
            def firstLevelIndex = 1000
            try {
                firstLevelIndex = firstLevelQuestions.indexOf(qn)
            } catch (x) {/*Ignore*/
            }
            def newFormSetting = new FormSetting([field                : qn.binding,
                                                  questionText         : qn.text,
                                                  typeOfQuestion       : FormSetting.SETTING_TEXT,
                                                  viewInTable          : false,
                                                  displayName          : qn.text,
                                                  orderOfDisplayInTable: idx])
            def dbFormSetting = misForm.id ? misForm.formSettings.find { it.field == qn.binding } : newFormSetting
            def formSetting = dbFormSetting ?: newFormSetting
            formSetting.xformType = qn.xformType.value

            if (!hasViewableColumns && firstLevelIndex < 10 && firstLevelIndex != -1) {
                formSetting.viewInTable = true
            }

            if (qn instanceof MultiSelectQuestion) {
                formSetting.typeOfQuestion = FormSetting.SETTING_MULT_SELECT
            }

            if (qn.xformType == XformType.GROUP) {
                //in future we might eventually get read of type of question and use xform type only
                formSetting.typeOfQuestion = FormSetting.SETTING_GROUP
            }

            def firstInstanceParent = flatten ? qn.firstRepeatParentOrForm : qn.firstInstanceParent
            if (!(firstInstanceParent instanceof markUpForm)) {
                formSetting.parentQuestion = firstInstanceParent.binding
            }

            /*TODO a question in repeat is not necessarily a repeat question
            *  however some reports depend on this, for a workround will create a field called
            * xform type
            * */
            if (qn instanceof RepeatQuestion || isInRepeat(qn)) {
                formSetting.typeOfQuestion = FormSetting.SETTING_REPEAT
                formSetting.viewInTable = false
            }
            formSetting.form = misForm

            if (!misForm.formSettings.any { it.field == formSetting.field }) {
                FormSetting.withNewTransaction { formSetting.save(failOnError: true, flush: true) }
            }

            if (qn instanceof ISelectionQuestion) {
                mayBeAddChoiceOptions(formSetting, qn)
            }
        }
    }

    private static String getXform(def studyId, def formId, def token) {
        def formVersionXml = withCentral {
            def xmlResponse = getFormVersionXml(studyId, formId, '___', token)
            if (xmlResponse) {
                def asString = XmlUtil.serialize(xmlResponse) as String
                return asString.replaceAll('tag0:', '').replaceAll(':tag0', '')
            }
            return null
        }
        formVersionXml
    }

    private static String deriveCentralFormName(def form) {
        return "_${form.xmlFormId}".toLowerCase()
    }

    private static String deriveCentralFormPostFix(String name) {
        return "${Form.TABLE_POSTFIX}_${createBindName(name)}".toLowerCase()
    }

    private static String createBindName(String name) {
        return name.replaceAll(/[^A-Za-z0-9 ]/, '').replaceAll(/\s+/, '_').toLowerCase()
    }

    static def mayBeAddChoiceOptions(FormSetting formSetting, ISelectionQuestion question) {
        def choiceOptions = question.options.collect { new ChoiceOption(choiceId: it.bind, text: it.text) }
        choiceOptions.each { choice ->
            choice.formSetting = formSetting
            if (!formSetting.choiceOptions.any { it.choiceId == choice.choiceId }) {
                ChoiceOption.withNewTransaction {
                    choice.save(failOnError: true, flush: true)
                }
            }

        }
    }

    private static boolean isInRepeat(IFormElement element) {
        def parent = element.firstInstanceParent
        while (parent) {
            if (parent instanceof RepeatQuestion) return true
            parent = parent.firstInstanceParent
        }
        return false
    }

    private static boolean isFlattenForm(markUpForm xform) {
        return true
    }
}
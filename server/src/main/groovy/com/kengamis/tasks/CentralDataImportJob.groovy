package com.kengamis.tasks

import com.kengamis.*
import com.kengamis.acl.KengaAclTableRecordIdentity
import com.kengamis.acl.KengaDataTable
import com.kengamis.acl.KengaGroupAclEntry
import com.omnitech.odkodata2sql.DataInserter
import com.omnitech.odkodata2sql.OdkOdataSlurper
import com.omnitech.odkodata2sql.SqlSchemaGen
import com.omnitech.odkodata2sql.model.OdkFormSource
import com.omnitech.odkodata2sql.model.OdkTable
import grails.util.Holders
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import groovy.util.logging.Log4j
import groovy.xml.XmlUtil
import org.openxdata.markup.*
import org.openxdata.markup.Form as markUpForm

import static com.kengamis.RestHelper.getCentralRestClient
import static com.kengamis.RestHelper.withCentral

@Log4j
class CentralDataImportJob extends Script {

    CentralService centralService
    static String centralId = Holders.grailsApplication.config.server.centralId as String

    @Override
    Object run() {
        try {
            centralService = AppHolder.bean('centralService')
            def token = centralService.auth()
            def study = Study.findByCentralId(centralId)
            syncCentralData(study, token)
        }
        catch (Exception e) {
            println("Exception: " + e.getMessage())
        }
    }

    static def syncCentralData(Study study, def token) {
        try {
            log.info("===========SYNCING STUDY [$study.name]")
            def forms = withCentral { listForms(study.centralId, token) }
            for (form in forms) {
                try {
                    println(">>>======================================syncing form:${form.name}")
//                    Form.withNewTransaction {
                        syncFormSetting(study, form, token)
//                    }
                }
                catch (Exception ex) {
                    ex.printStackTrace()
                    log.error("ERROR:::: Failed to synchronise [${form.name}] !!!", Util.sanitize(ex))
                }
                syncFormData(study, form, token)
            }
        }
        catch (Exception ex) {
            log.error("Failed to Synchronise Form, [$study]", ex)
        }
        log.info("=============Finished syncing data For Study [$study.name]========\n\n")
    }

    static def syncFormSetting(Study study, def form, def token) {
        def studyCentralId = study.centralId as String
        def formCentralId = form['xmlFormId'] as String
        def postFix = deriveCentralFormPostFix(form['name'].toString())
        def slurper = new OdkOdataSlurper(getCentralRestClient(), token)
                .projectId(studyCentralId)
                .formId(formCentralId)
        OdkFormSource formSource = slurper.mapToModel()
        def tableName = formSource.mainTable.name as String
        def odkTableName = new OdkTable(name: tableName)
        def finalTableName = SqlSchemaGen.createTableName(odkTableName, postFix)
        log.info("  -> Derived table for form [$study.name -> $formCentralId] = [[$finalTableName]]")

        def misForm = Form.findByName(finalTableName) ?: new Form(name: finalTableName, study: study,
                centralId: formCentralId, displayName: form.name)
//        Form.withNewTransaction { misForm.save(failOnError: true, flush: true) }
        misForm.save(flush:true, failOnError: true)
        log.info("  -> Done Saving form[$form]")

        def versions = withCentral { getFormVersions(study.centralId, formCentralId, token) }
        for (version in versions) {
            log.info(version)
            if(version.version == ""){
                importVersion(study, misForm, "___", token)
            }else{
                importVersion(study, misForm, version.version, token)
            }
        }
//        importVersion(study, misForm, '___', token)
        misForm.save(flush:true, failOnError: true)
        log.info("  -> Done Importing form[$form] Settings...")
    }

    static def syncFormData(Study study, def form, def token) {
        log.info("======= Syncing Form Data [$study.name -> $form.name] ========================")

        def studyCentralId = study.centralId as String
        def formCentralId = form['xmlFormId'] as String
        def formName = form['name'] as String
        def misForm = Form.findByCentralId(formCentralId)
        def misFormObject = Form.findByDisplayName(formName)
        def misFormObjectName = misFormObject.name
        def kengaDataTable = KengaDataTable.findByTableName(misFormObjectName)
        if (misForm != null && misForm.syncMode) {
            def postFix = deriveCentralFormPostFix(formName)
            def slurper = new OdkOdataSlurper(getCentralRestClient(), token)
                    .projectId(studyCentralId)
                    .formId(formCentralId)
            OdkFormSource formSource = slurper.mapToModel()
            def data = withCentral { getDataDocument(token, studyCentralId, formCentralId, 'Submissions', 0, 100000, true) }
            def records = data['value']
            records.each { record ->
                def central_system_info = record['__system']
                def central_user_name = central_system_info['submitterName']
                AppHolder.withMisSqlNonTx {
                    new DataInserter(connection, JsonOutput.toJson(record), formSource, true)
                            .setConfig([
                                    'central_user_name': central_user_name,
                            ])
                            .setCoreTablePostFix(postFix)
                            .insert()
                    def recordId = record.__id
                    generateKengaAclRecordIdentities(kengaDataTable, recordId)
                }
            }
            // then create the acls from here
            try{
                createAclsForRecords()
            }catch(Exception ex) {
                ex.printStackTrace()
            }


        } else {
            log.info(" ======= The sync mode of form (${form.name}) is not enabled =======")
        }

    }

    static def generateKengaAclRecordIdentities(KengaDataTable kengaDataTable, recordId) {
        try {
            // if the record doesnt exist in the table then add it
            if (!(KengaAclTableRecordIdentity.findByDataTableRecordId(recordId))) {
                new KengaAclTableRecordIdentity(
                        kengaDataTable: kengaDataTable,
                        dataTableRecordId: recordId
                ).save(flush: true, failOnError: true)
            }

        } catch (Exception ex) {
            log.error(ex.getMessage())
        }
    }

    static def createAclsForRecords() {
        QueryTable.all.each { qt ->
            def groupId = qt.kengaGroupId
            def permission = qt.permission
            def queryArray = new JsonSlurper().parseText(qt.query)
            def queryData = queryArray['queryData']
            print(queryData)
            queryData.each { it ->
                def formName = it['form']
                def grpConditionQuery = it['groupConditionQuery']
                def kengaGroup = KengaGroup.get(groupId)
                def kengaDataTable = KengaDataTable.findByTableName(formName as String)

                //query records
                def data = AppHolder.withMisSqlNonTx {
                    def query = "select * from ${formName} ${grpConditionQuery}"
                    log.info(query)
                    rows(query.toString())
                }
                log.info("==============size${data.size()}")

                if (data.size() > 0) {
                    def idLabel = kengaDataTable.idLabel
                    createAcls(data, groupId, permission, idLabel)
                    def parentGroupId = kengaGroup.parentGroup.collect { it.id }[0]

                    while (parentGroupId != null) {
                        // getting the parent object which will be used to create the acl
                        def myCurrentObject = kengaGroup.get(parentGroupId)

                        // create acl for the parent
                        createAcls(data, myCurrentObject, permission, idLabel)

                        // update the parent ID to the new parent of the current parent
                        parentGroupId = myCurrentObject.parentGroup.collect { it.id }[0]
                    }
                }


            }

        }

    }

    static def createAcls(aclRecords, groupId, permissionNumber, idLabel) {
        aclRecords.each { recordz ->
            def kengaAclTableRecordIdentity = KengaAclTableRecordIdentity.findByDataTableRecordId(recordz."$idLabel")
//            to make sure that the acl for a group is not repeated
            def groupObject = KengaGroup.findById(groupId)

            if (!(KengaGroupAclEntry.findByKengaAclTableRecordIdentityAndKengaGroup(kengaAclTableRecordIdentity, groupObject))) {
                new KengaGroupAclEntry(
                        kengaAclTableRecordIdentity: kengaAclTableRecordIdentity,
                        kengaGroup: groupId,
                        mask: permissionNumber
                ).save(flush: true, failOnError: true)
            }
        }
    }

    static def importVersion(Study study, Form misForm, def versionName, def token) {
        log.info("        ->Importing version settings [$misForm]-[$versionName]")
        def hasViewableColumns = misForm.hasViewableColumns()
        String formVersionXml = getXform(study.centralId, misForm.centralId, token)
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
            def settingAlreadyExist = misForm.hasFormSetting(misForm, formSetting.field)
            if (!settingAlreadyExist) {
//                FormSetting.withNewTransaction {
                    formSetting.save(failOnError: true, flush: true)
//                }
            }
            formSetting.save(flush:true, failOnError: true
            )

            if (qn instanceof ISelectionQuestion) {
                mayBeAddChoiceOptions(formSetting, qn)
            }
        }
    }

    static String getXform(def studyId, def formId, def token) {
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

    static String deriveCentralFormPostFix(String name) {
        return "${Form.TABLE_POSTFIX}_${createBindName(name)}".toLowerCase()
    }

    static String createBindName(String name) {
        return name.replaceAll(/[^A-Za-z0-9 ]/, '').replaceAll(/\s+/, '_').toLowerCase()
    }

    static def mayBeAddChoiceOptions(FormSetting formSetting, ISelectionQuestion question) {
        def choiceOptions = question.options.collect { new ChoiceOption(choiceId: it.bind, text: it.text) }
        choiceOptions.each { choice ->
            choice.formSetting = formSetting
            if (!formSetting.choiceOptions.any { it.choiceId == choice.choiceId }) {
//               ChoiceOption.withNewTransaction {
                   choice.save(failOnError: true, flush: true)
//               }
            }
        }
    }

    static boolean isInRepeat(IFormElement element) {
        def parent = element.firstInstanceParent
        while (parent) {
            if (parent instanceof RepeatQuestion) return true
            parent = parent.firstInstanceParent
        }
        return false
    }

    static boolean isFlattenForm(markUpForm xform) {
        return true
    }
}

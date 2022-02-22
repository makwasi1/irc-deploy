package com.kengamis.exporter

import com.kengamis.AppHolder
import com.kengamis.Form
import com.kengamis.FormSetting
import com.kengamis.User
import com.kengamis.Util
import com.kengamis.query.QueryHelper
import fuzzycsv.FuzzyCSV
import fuzzycsv.FuzzyCSVTable
import groovy.sql.Sql
import groovy.util.logging.Slf4j
import org.openxdata.markup.XformType

import java.sql.ResultSet

@Slf4j
class DataExporter {
    String formTable
    Map params
    Form form
    User user
    def choiceOptions = [:]

    DataExporter(String formTable, Map params) {
        this.params = params
        this.formTable = formTable
        this.params.formtable = formTable
        init()
    }

    def init() {
        form = Form.findByName(formTable)
        user = AppHolder.currentUser()
        def selectColumns = FormSetting.findAllByFormAndXformTypeInList(form, ['SELECT', 'SELECT1', 'SELECT1_DYNAMIC'])
        selectColumns.each { setting ->
            setting.choiceOptions.each { opt ->
                choiceOptions.put(opt.choiceId, opt.text)
            }
        }
    }

    def exportToExcel() {
        def exportedData = []
        Map<String, String> _queries = buildSelectSql()
        if (!_queries) {
            exportedData = []
        } else {
            def topQuery = _queries[formTable]
            AppHolder.withMisSql {
                query(topQuery) { ResultSet rs ->
                    exportedData = getExportedData(rs)
                }
            }
        }
        return exportedData
    }

    def setFileName() {
        def name = Util.cleanFileName(form.humanReadableName())
        def finalName = Util.truncateString(name, 80)
        return finalName
    }

    private List<FormSetting> getColumnHeaderSettings(ResultSet rs) {
        def dbColumnHeaders = FuzzyCSV.getColumns(rs.metaData)
        def headerSettings = dbColumnHeaders.collect { String dbField ->
            form.findFormSetting(dbField)
        }
        return headerSettings
    }

    private List<Map<String, String>> getExportedData(ResultSet rs) {
        def result = []
        def mapList = FuzzyCSVTable.toCSV(rs).toMapList()
        def headers = getColumnHeaderSettings(rs)
        mapList.each { record ->
            def eachRecord = [:]
            record.eachWithIndex { rec, idx ->
                def header = headers.get(idx)
                if (header != null) {
                    if (header.typeOfQuestion == FormSetting.SETTING_MULT_SELECT) {
                        eachRecord["${header.questionText}".replaceAll("(\n)","")] = rec.value ? rec.value.split(" ").collect {
                            choiceOptions."$it" ?: (it ?: "")
                        }.join(" , ") : rec.value
                    }
                    else {
                        eachRecord["${header.questionText}".replaceAll("(\n)","")] = choiceOptions."$rec.value" ?: (rec.value ?: "")
                    }
                } else {
                    eachRecord["${rec.key}".replaceAll("(\n)","")] = choiceOptions."$rec.value" ?: (rec.value ?: "")
                }
            }
            result << eachRecord
        }
        return result
    }

    private Map<String, String> buildSelectSql(Boolean includeNames = true) {
        def qh = new QueryHelper(params, user)


        def allQuestions = form.findAllFirstLevelQuestionsInFormAndFirstLevelGroups(60)

        qh.headers = allQuestions.findAll {
            !(it.xformType in [XformType.PICTURE.value, XformType.AUDIO.value, XformType.VIDEO.value])
        }.sort { it.orderOfDisplayInTable }
        qh.headers.add(0, form.metaUsername())
        qh.headers.add(1, form.metaDateCreated())
        qh.initBaseTable()


        def map = [(formTable): qh.query]


        def qns = form.findAllRepeatAndGroupQns()
        for (formSetting in qns) {
            def repeatQh = new QueryHelper(params, user)
            repeatQh.setUserBaseTable(formTable)
            repeatQh.setJoinType('inner')

            repeatQh.headers.add(0, form.metaId(formSetting.field))
            repeatQh.headers.add(1, form.metaUsername(formSetting.field))
            repeatQh.headers.add(2, form.metaDateCreated(formSetting.field))
            repeatQh.headers.addAll(formSetting.childQuestions)

            repeatQh.headers = repeatQh.headers.unique { it.field }

            repeatQh.initBaseTable()

            def fs = "$formSetting.field-[$formSetting.questionText]"

            if (!includeNames) {
                fs = FormSetting.findByFormAndField(Form.findByName(formTable), formSetting.field).getTableName()
            }

            map[fs] = repeatQh.query
        }
        return map
    }
}
package com.kengamis

import com.kengamis.query.QueryHelper
import com.kengamis.query.security.Permission
import grails.core.GrailsApplication
import grails.validation.ValidationException

import static com.kengamis.Role.*
import static fuzzycsv.FuzzyCSVTable.tbl
import static fuzzycsv.FuzzyCSVTable.toCSV
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class EntityViewController {

    EntityViewService entityViewService
    def springSecurityService
    GrailsApplication grailsApplication

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        def entityViews = EntityView.findAll().collect { entityView ->
            def entityViewFilters = entityView.filters.collect { [id: it.id, name: it.name] }
            [id: entityView.id, name: entityView.name, tableName: entityView.tableName,
             description: entityView.description, dateCreated: entityView.dateCreated, entityId: entityView.misEntity.id,
             entityViewFilters: entityViewFilters]
        }
        respond entityViews
    }

    def show(String id) {
        def entityView = entityViewService.get(id)
        def entityViewFields = entityView.viewFields
        def entityViewFilters = entityView.filters.collect { [id: it.id, name: it.name] }
        def entityViewReturned = [entityView: entityView,
                                  entityViewFields: entityViewFields, entityViewFilters: entityViewFilters]
        respond entityViewReturned
    }

    @Transactional
    def save(EntityView entityView) {
        if (entityView == null) {
            render status: NOT_FOUND
            return
        }
        if (entityView.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond entityView.errors
            return
        }

        try {
            entityViewService.save(entityView)
            createView(entityView)

        } catch (ValidationException e) {
            respond entityView.errors
            return
        }

        respond entityView, [status: CREATED, view: "show"]
    }

    @Transactional
    def update(EntityView entityView) {
        if (entityView == null) {
            render status: NOT_FOUND
            return
        }
        if (entityView.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond entityView.errors
            return
        }

        try {
            entityViewService.save(entityView)
            createView(entityView)
        } catch (ValidationException e) {
            respond entityView.errors
            return
        }

        respond entityView, [status: OK, view: "show"]
    }

    @Transactional
    def delete(String id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        def entityView = EntityView.get(id)
        def entityViewTable = entityView.tableName
        def deleteTableQuery = " DROP VIEW IF EXISTS ${entityViewTable}".toString()
        def results = AppHolder.withMisSqlNonTx { execute(deleteTableQuery) }
        if (!results) {
            log.info("Views successfully deleted")
        }

        entityViewService.delete(id)

        render status: NO_CONTENT
    }

    def createView(EntityView entityView) {
        if (entityView.viewQuery) {
            AppHolder.withMisSqlNonTx {
                execute(entityView.viewQuery.toString())
            }
        }
    }

    def viewData() {
        def entityView = EntityView.get(params.id)
        params.formtable = entityView.tableName
        def query

        if (!entityView.ignoreUserContext) {
            query = """
                SELECT ${entityView.viewFields.collect { it.name }.join(",")} FROM ${entityView.tableName}
                """
        } else {
            query = """
                SELECT ${entityView.viewFields.collect { it.name }.join(",")} FROM ${entityView.tableName}
               """
        }

        def data = AppHolder.withMisSql {
            toCSV(it, query.toString())
        }.csv
        def dataMapList = tbl(data).toMapList()
        def headers
        def keyField = entityView.keyField
        def dataList
        if (dataMapList.isEmpty()) {
            headers = []
            dataList = []
        } else {
            headers = entityView.viewFields.sort { it.orderOfDisplay }.collect { it.name }
            dataList = dataMapList
        }
        def entityViewData = [dataList: dataList, headers: headers, keyField: keyField, entity: entityView]
        respond entityViewData
    }
}

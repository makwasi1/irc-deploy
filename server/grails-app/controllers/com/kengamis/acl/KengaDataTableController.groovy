package com.kengamis.acl

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class KengaDataTableController {

    KengaDataTableService kengaDataTableService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond kengaDataTableService.list(params), model:[kengaDataTableCount: kengaDataTableService.count()]
    }

    def show(Long id) {
        respond kengaDataTableService.get(id)
    }

    @Transactional
    def save(KengaDataTable kengaDataTable) {
        if (kengaDataTable == null) {
            render status: NOT_FOUND
            return
        }
        if (kengaDataTable.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond kengaDataTable.errors
            return
        }

        try {
            kengaDataTableService.save(kengaDataTable)
        } catch (ValidationException e) {
            respond kengaDataTable.errors
            return
        }

        respond kengaDataTable, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(KengaDataTable kengaDataTable) {
        if (kengaDataTable == null) {
            render status: NOT_FOUND
            return
        }
        if (kengaDataTable.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond kengaDataTable.errors
            return
        }

        try {
            kengaDataTableService.save(kengaDataTable)
        } catch (ValidationException e) {
            respond kengaDataTable.errors
            return
        }

        respond kengaDataTable, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        kengaDataTableService.delete(id)

        render status: NO_CONTENT
    }
}

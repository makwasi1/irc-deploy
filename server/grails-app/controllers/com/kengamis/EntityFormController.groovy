package com.kengamis

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class EntityFormController {

    EntityFormService entityFormService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond entityFormService.list(params), model:[entityFormCount: entityFormService.count()]
    }

    def show(Long id) {
        respond entityFormService.get(id)
    }

    @Transactional
    def save(EntityForm entityForm) {
        if (entityForm == null) {
            render status: NOT_FOUND
            return
        }
        if (entityForm.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond entityForm.errors
            return
        }

        try {
            entityFormService.save(entityForm)
        } catch (ValidationException e) {
            respond entityForm.errors
            return
        }

        respond entityForm, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(EntityForm entityForm) {
        if (entityForm == null) {
            render status: NOT_FOUND
            return
        }
        if (entityForm.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond entityForm.errors
            return
        }

        try {
            entityFormService.save(entityForm)
        } catch (ValidationException e) {
            respond entityForm.errors
            return
        }

        respond entityForm, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        entityFormService.delete(id)

        render status: NO_CONTENT
    }
}

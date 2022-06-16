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
class TempController {

    TempService tempService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond tempService.list(params), model:[tempCount: tempService.count()]
    }

    def show(String id) {
        respond tempService.get(id)
    }

    @Transactional
    def save(Temp temp) {
        println temp.errors
        if (temp == null) {
            render status: NOT_FOUND
            return
        }
        if (temp.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond temp.errors
            return
        }

        try {
            tempService.save(temp)
        } catch (ValidationException e) {
            respond temp.errors
            return
        }

        respond temp, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Temp temp) {
        println temp.errors
        if (temp == null) {
            render status: NOT_FOUND
            return
        }
        if (temp.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond temp.errors
            return
        }

        try {
            tempService.save(temp)
        } catch (ValidationException e) {
            respond temp.errors
            return
        }

        respond temp, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        tempService.delete(id)

        render status: NO_CONTENT
    }
}

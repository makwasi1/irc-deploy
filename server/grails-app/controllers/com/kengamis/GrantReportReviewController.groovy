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
class GrantReportReviewController {

    GrantReportReviewService grantReportReviewService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond grantReportReviewService.list(params), model:[grantReportReviewCount: grantReportReviewService.count()]
    }

    def show(String id) {
        //the id passed here is the grantId, therefore we find by grantId not Id
        respond GrantReportReview.findByGrantId(id)
    }

    @Transactional
    def save(GrantReportReview grantReportReview) {
        println grantReportReview.errors
        if (grantReportReview == null) {
            render status: NOT_FOUND
            return
        }
        if (grantReportReview.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond grantReportReview.errors
            return
        }

        try {
            grantReportReviewService.save(grantReportReview)
        } catch (ValidationException e) {
            respond grantReportReview.errors
            return
        }

        respond grantReportReview, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(GrantReportReview grantReportReview) {
        if (grantReportReview == null) {
            render status: NOT_FOUND
            return
        }
        if (grantReportReview.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond grantReportReview.errors
            return
        }

        try {
            grantReportReviewService.save(grantReportReview)
        } catch (ValidationException e) {
            respond grantReportReview.errors
            return
        }

        respond grantReportReview, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        grantReportReviewService.delete(id)

        render status: NO_CONTENT
    }

    def getByProcessInstanceId() {
        def results = [results: GrantReportReview.findByProcessInstanceId(params.id)]
        respond results
    }
}

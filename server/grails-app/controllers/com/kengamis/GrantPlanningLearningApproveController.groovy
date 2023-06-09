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
class GrantPlanningLearningApproveController {

    GrantPlanningLearningApproveService grantPlanningLearningApproveService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond grantPlanningLearningApproveService.list(params), model:[grantPlanningLearningApproveCount: grantPlanningLearningApproveService.count()]
    }

    def show(String id) {
        //the id passed here is the grantId, therefore we find by grantId not Id
        respond GrantPlanningLearningApprove.findByGrantId(id)
    }

    @Transactional
    def save(GrantPlanningLearningApprove grantPlanningLearningApprove) {
        if (grantPlanningLearningApprove == null) {
            render status: NOT_FOUND
            return
        }
        if (grantPlanningLearningApprove.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond grantPlanningLearningApprove.errors
            return
        }

        try {
            grantPlanningLearningApproveService.save(grantPlanningLearningApprove)
        } catch (ValidationException e) {
            respond grantPlanningLearningApprove.errors
            return
        }

        respond grantPlanningLearningApprove, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(GrantPlanningLearningApprove grantPlanningLearningApprove) {
        if (grantPlanningLearningApprove == null) {
            render status: NOT_FOUND
            return
        }
        if (grantPlanningLearningApprove.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond grantPlanningLearningApprove.errors
            return
        }

        try {
            grantPlanningLearningApproveService.save(grantPlanningLearningApprove)
        } catch (ValidationException e) {
            respond grantPlanningLearningApprove.errors
            return
        }

        respond grantPlanningLearningApprove, [status: OK, view:"show"]
    }

    @Transactional
    def delete(String id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        grantPlanningLearningApproveService.delete(id)

        render status: NO_CONTENT
    }

    def getByProcessInstanceId() {
        def results = [results: GrantPlanningLearningApprove.findByProcessInstanceId(params.id)]
        respond results
    }
}

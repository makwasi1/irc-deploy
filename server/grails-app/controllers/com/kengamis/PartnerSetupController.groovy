package com.kengamis

import com.kengamis.tasks.StartCamundaInstancesJob
import grails.validation.ValidationException
import groovy.json.JsonSlurper
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class PartnerSetupController {

    PartnerSetupService partnerSetupService
    ProgramPartnerService programPartnerService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        //params.max = Math.min(max ?: 10, 100)

        def partnerSetups = partnerSetupService.list(params)
        def list = []

        partnerSetups.each { PartnerSetup setup ->
            def partner = ProgramPartner.findById(setup.partnerId)

            list << [id         : setup.id,
                     partner    : partner ? partner.cluster : '',
                     lastUpdated: setup.lastUpdated,
                     dateCreated: setup.dateCreated]
        }
        respond list
    }

    def show(Long id) {
        respond partnerSetupService.get(id)
    }

    @Transactional
    def save(PartnerSetup partnerSetup) {
        if (partnerSetup == null) {
            render status: NOT_FOUND
            return
        }
        if (partnerSetup.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond partnerSetup.errors
            return
        }

        try {
            partnerSetupService.save(partnerSetup)
            saveWorkPlanValues(partnerSetup)
        } catch (ValidationException e) {
            respond partnerSetup.errors
            return
        }

        respond partnerSetup, [status: CREATED, view: "show"]
    }

    @Transactional
    def update(PartnerSetup partnerSetup) {
        if (partnerSetup == null) {
            render status: NOT_FOUND
            return
        }
        if (partnerSetup.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond partnerSetup.errors
            return
        }

        try {
            partnerSetupService.save(partnerSetup)
            saveWorkPlanValues(partnerSetup)
        } catch (ValidationException e) {
            respond partnerSetup.errors
            return
        }

        respond partnerSetup, [status: OK, view: "show"]
    }

    @Transactional
    def delete(String id) {
        if (id == null && params.id == null) {
            render status: NOT_FOUND
            return
        }

        //delete all tasks, calendar trigger dates and reports linked to this partner
        def partnerSetup = partnerSetupService.get(params.id)
        def tasks = TaskList.findAllByInputVariablesIlike('%' + partnerSetup.partnerId + '%')
        tasks.each { TaskList it ->
            def deletedFromCamunda = deleteProcessInstance(it.processInstanceId)
            if (deletedFromCamunda) {
                ReportForm.findAllByProcessInstanceId(it.processInstanceId).each { ReportForm rf ->
                    ReportFormFinancial.findAllByReportId(it.id).each { ReportFormFinancial rff -> rff.delete() }
                    ReportFormPerformance.findAllByReportId(it.id).each { ReportFormPerformance rfp -> rfp.delete() }
                    rf.delete()
                }
                ReportFormFiles.findAllByProcessInstanceId(it.processInstanceId).each { ReportFormFiles files -> files.delete() }
                ReportFormRecommendations.findAllByProcessInstanceId(it.processInstanceId).each { ReportFormRecommendations recommendations -> recommendations.delete() }
                ReportFormComments.findAllByProcessInstanceId(it.processInstanceId).each { ReportFormComments comments -> comments.delete() }
                it.delete()
            }
        }
        CalendarTriggerDates.findAllByPartnerSetupId(partnerSetup.id).each { CalendarTriggerDates it -> it.delete() }
        PartnerSetupBudget.findAllByPartnerSetupId(partnerSetup.id).each {PartnerSetupBudget it -> it.delete()}
        PartnerSetupMilestones.findAllByPartnerSetupId(partnerSetup.id).each {PartnerSetupMilestones it -> it.delete()}
        PartnerSetupDisbursementPlan.findAllByPartnerSetupId(partnerSetup.id).each {PartnerSetupDisbursementPlan it -> it.delete()}

        partnerSetupService.delete(id ?: params.id)

        render status: NO_CONTENT
    }

    def getPartnerSetupRecord() {
        def map = [setup: PartnerSetup.findById(params.id)]
        respond map
    }

    def saveWorkPlanValues(PartnerSetup record) {
        def slurper = new JsonSlurper()
        def partnerSetupId = record.id
        def setupValues = slurper.parseText(record.setupValues)
        def budget = setupValues['budget']
        def disbursementPlan = setupValues['disbursementPlan']
        def milestones = slurper.parseText(setupValues['indicators'] as String)

        budget.each { b ->
            def psb = PartnerSetupBudget.findAllByPartnerSetupIdAndBudgetLine(partnerSetupId, b['budgetLine'] as String)
            if (psb.size() > 0) {
                psb.each {
                    it.partnerSetupId = partnerSetupId
                    it.budgetLine = b['budgetLine']
                    it.milestoneId = b['indicatorId']
                    it.approvedAmount = b['approvedAmount']
                    it.totalSpent = b['totalSpent']
                    it.save(flush: true, failOnError: true)
                }
            } else {
                new PartnerSetupBudget(
                        partnerSetupId: partnerSetupId,
                        budgetLine: b['budgetLine'],
                        milestoneId: b['indicatorId'],
                        approvedAmount: b['approvedAmount'],
                        totalSpent: b['totalSpent'],
                ).save(flush: true, failOnError: true)
            }
        }

        disbursementPlan.each { d ->
            def psd = PartnerSetupDisbursementPlan.findAllByPartnerSetupIdAndDatePeriod(partnerSetupId, d['datePeriod'] as String)
            if (psd.size() > 0) {
                psd.each {
                    it.partnerSetupId = partnerSetupId
                    it.datePeriod = d['datePeriod']
                    it.startDate = d['startDate']
                    it.endDate = d['endDate']
                    it.disbursement = d['disbursement']
                    it.save(flush: true, failOnError: true)
                }
            } else {
                new PartnerSetupDisbursementPlan(
                        partnerSetupId: partnerSetupId,
                        datePeriod: d['datePeriod'],
                        startDate: d['startDate'],
                        endDate: d['endDate'],
                        disbursement: d['disbursement']
                ).save(flush: true, failOnError: true)
            }
        }

        milestones.each { m ->
            def psm = PartnerSetupMilestones.findAllByPartnerSetupIdAndName(partnerSetupId, m['name'] as String)
            if (psm.size() > 0) {
                psm.each {
                    it.partnerSetupId = partnerSetupId
                    it.name = m['name']
                    it.milestoneId = m['milestoneId']
                    it.overallTarget = m['overallTarget']
                    it.disaggregation = m['disaggregation']
                    it.save(flush: true, failOnError: true)
                }
            } else {
                new PartnerSetupMilestones(
                        partnerSetupId: partnerSetupId,
                        name: m['name'],
                        milestoneId: m['milestoneId'],
                        overallTarget: m['overallTarget'],
                        disaggregation: m['disaggregation']
                ).save(flush: true, failOnError: true)
            }
        }

    }

    static boolean deleteProcessInstance(id) {
        def http = new HTTPBuilder(StartCamundaInstancesJob.camundaApiUrl + "/delete/$id")
        boolean deleted = false
        http.request(Method.POST, ContentType.JSON) { req ->
            headers.Accept = 'application/json'
            requestContentType = ContentType.JSON
            response.success = { resp, json ->
                println("Camunda :: deleteProcessInstance() True [ " + json.text + " ]")
                deleted = true
            }
            response.failure = { resp ->
                println("Camunda :: deleteProcessInstance() False [ " + resp.status + " ]")
            }
        }
        return deleted
    }
}

package com.kengamis

import fuzzycsv.FuzzyCSVTable
import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper

import static org.springframework.http.HttpStatus.*

@ReadOnly
class TaskListController {

    TaskListService taskListService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        //params.max = Math.min(max ?: 10, 100)

        def taskListMapList = taskListService.list(params)
        def tasks = []

        taskListMapList.each{TaskList task ->
            def slurper = new JsonSlurper()
            def variables = slurper.parseText(task.inputVariables)
            def partnerSetupId = '', startDate = '', partnerId = '', programId = '', endDate = '', groupId = '', period = ''

            variables['data'].each {
                if(it.key=='PartnerSetupId') partnerSetupId = it.value
                if(it.key=='Period') period = it.value
                if(it.key=='StartDate') startDate = it.value
                if(it.key=='PartnerId') partnerId = it.value
                if(it.key=='ProgramId') programId = it.value
                if(it.key=='EndDate') endDate = it.value
                if(it.key=='GroupId') groupId = it.value
            }

            def programPartner = ProgramPartner.findById(partnerId)
            def program = Program.findById(programId)

            if(programPartner==null) programPartner = [name:'']
            if(program==null) program = [title:'']

            if(task.status != "completed")
                tasks << [id: task.id,
                        taskName : task.taskName,
                        partnerSetupId: partnerSetupId,
                        startDate : startDate,
                        partnerId : partnerId,
                        partnerName : programPartner.name,
                        programId : programId,
                        programName : program.title,
                        endDate : endDate,
                        groupId : groupId,
                        reportingPeriod : period,
                        outputVariables : task.outputVariables,
                        processInstanceId : task.processInstanceId,
                        taskDefinitionKey : task.taskDefinitionKey,
                        dateCreated: task.dateCreated,
                        status: task.status]
        }
        respond tasks
    }

    def getTaskRecord() {
        def task = TaskList.get(params.id)

        def slurper = new JsonSlurper()
        def variables = slurper.parseText(task.inputVariables)
        def partnerSetupId = '', startDate = '', partnerId = '', programId = '', endDate = '', groupId = '', period = ''

        variables['data'].each {
            if(it.key=='PartnerSetupId') partnerSetupId = it.value
            if(it.key=='Period') period = it.value
            if(it.key=='StartDate') startDate = it.value
            if(it.key=='PartnerId') partnerId = it.value
            if(it.key=='ProgramId') programId = it.value
            if(it.key=='EndDate') endDate = it.value
            if(it.key=='GroupId') groupId = it.value
        }

        def programPartner = ProgramPartner.findById(partnerId)
        def program = Program.findById(programId)

        def t = [id: task.id,
                taskName : task.taskName,
                partnerSetupId: partnerSetupId,
                startDate : startDate,
                partnerId : partnerId,
                partnerName : programPartner.name,
                programId : programId,
                programName : program.title,
                endDate : endDate,
                groupId : groupId,
                reportingPeriod : period,
                outputVariables : task.outputVariables,
                processInstanceId : task.processInstanceId,
                taskDefinitionKey : task.taskDefinitionKey,
                dateCreated: task.dateCreated,
                status: task.status]
        respond t
    }

    def show(Long id) {
        respond taskListService.get(id)
    }

    @Transactional
    def save(TaskList taskList) {
        if (taskList == null) {
            render status: NOT_FOUND
            return
        }
        if (taskList.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond taskList.errors
            return
        }

        try {
            taskListService.save(taskList)
        } catch (ValidationException e) {
            respond taskList.errors
            return
        }

        respond taskList, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(TaskList taskList) {
        if (taskList == null) {
            render status: NOT_FOUND
            return
        }
        if (taskList.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond taskList.errors
            return
        }

        try {
            taskListService.save(taskList)
        } catch (ValidationException e) {
            respond taskList.errors
            return
        }

        respond taskList, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        taskListService.delete(id)

        render status: NO_CONTENT
    }
}

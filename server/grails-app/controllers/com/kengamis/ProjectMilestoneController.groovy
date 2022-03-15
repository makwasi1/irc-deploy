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
class ProjectMilestoneController {

    ProjectMilestoneService projectMilestoneService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        def projectMilestones = []
        projectMilestoneService.list(params).each { projectMilestone ->
            def newProjectMilestoneObject = [:]
            def categoryId = projectMilestone.programCategory.id
            def category = ProgramCategory.findById(categoryId)
            def program = Program.findById(category.program.id)
            newProjectMilestoneObject['id'] = projectMilestone.id
            newProjectMilestoneObject['name'] = projectMilestone.name
            newProjectMilestoneObject['description'] = projectMilestone.description
            newProjectMilestoneObject['reportingQuery'] = projectMilestone.reportingQuery
            newProjectMilestoneObject['dashboardQuery'] = projectMilestone.dashboardQuery
            newProjectMilestoneObject['categoryId'] = categoryId
            newProjectMilestoneObject['dateCreated'] = projectMilestone.dateCreated
            newProjectMilestoneObject['lastUpdated'] = projectMilestone.lastUpdated
            newProjectMilestoneObject['category'] = category.name
            newProjectMilestoneObject['program'] = program.title
            projectMilestones << newProjectMilestoneObject
        }
        respond projectMilestones
    }

    def show(String id) {
        def projectMilestone = projectMilestoneService.get(id)
        def newProjectMilestoneObject = [:]
        def categoryId = projectMilestone.programCategory.id
        def category = ProgramCategory.findById(categoryId)
        def program = Program.findById(category.program.id)
        newProjectMilestoneObject['id'] = projectMilestone.id
        newProjectMilestoneObject['name'] = projectMilestone.name
        newProjectMilestoneObject['description'] = projectMilestone.description
        newProjectMilestoneObject['reportingQuery'] = projectMilestone.reportingQuery
        newProjectMilestoneObject['dashboardQuery'] = projectMilestone.dashboardQuery
        newProjectMilestoneObject['categoryId'] = categoryId
        newProjectMilestoneObject['dateCreated'] = projectMilestone.dateCreated
        newProjectMilestoneObject['lastUpdated'] = projectMilestone.lastUpdated
        newProjectMilestoneObject['category'] = category.name
        newProjectMilestoneObject['program'] = program.title
        newProjectMilestoneObject['programId'] = program.title
        respond newProjectMilestoneObject
    }

    @Transactional
    def save(ProjectMilestone projectMilestone) {
        if (projectMilestone == null) {
            render status: NOT_FOUND
            return
        }
        if (projectMilestone.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond projectMilestone.errors
            return
        }

        try {
            projectMilestoneService.save(projectMilestone)
        } catch (ValidationException e) {
            respond projectMilestone.errors
            return
        }

        respond projectMilestone, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(ProjectMilestone projectMilestone) {
        if (projectMilestone == null) {
            render status: NOT_FOUND
            return
        }
        if (projectMilestone.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond projectMilestone.errors
            return
        }

        try {
            projectMilestoneService.save(projectMilestone)
        } catch (ValidationException e) {
            respond projectMilestone.errors
            return
        }

        respond projectMilestone, [status: OK, view:"show"]
    }

    @Transactional
    def delete(String id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        projectMilestoneService.delete(id)

        render status: NO_CONTENT
    }

    def runQuery() {
        def milestoneData
        def milestoneQuery = params.query as String
        try {
            def query = "${milestoneQuery}".toString()
            def data = AppHolder.withMisSql {
                toCSV(it, query)
            }.csv

            def dataMapList = tbl(data).toMapList()
            def headers = dataMapList.get(0).keySet()
            milestoneData = [dataList: dataMapList, headerList: headers]
        }
        catch (Exception e) {
            log.error("Error fetching data", e)
            milestoneData = [dataList: [], headerList: []]
        }
        respond milestoneData
    }
}
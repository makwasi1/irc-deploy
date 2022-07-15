package com.kengamis.tasks

import com.kengamis.AppHolder
import com.kengamis.Archive
import com.kengamis.TaskList
import grails.gorm.transactions.Transactional
import groovy.json.JsonBuilder
import groovy.sql.Sql
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

class TaskListSyncJob extends Script {
    Sql sql = sql
    static def url = StartCamundaInstancesJob.camundaApiUrl

    @Override
    Object run() {
        //archive reports
        archiveReport()

        downloadTasks(url + '/get-tasks/ACTIVITY_REPORTING/' + '0/50')
        downloadTasks(url + '/get-tasks/PROGRESS_REPORTING/' + '0/50')
        downloadTasks(url + '/get-tasks/IRC_REFERRAL/' + '0/50')
        downloadTasks(url + '/get-tasks/IRC_FEEDBACK/' + '0/50')
        //send data to workflow
        def data = TaskList.where {status == 'completed' && synced == 'false' }.findAll()
        data.each {  sendTasksToWorkflow(it as TaskList) }

        return null
    }

    static def saveTaskToDB(task, variables) {
        def status = "not_started"
        def synced = "false"

        def taskList = TaskList.findByTaskId("${task['id']}") ?: new TaskList(
                taskId: "${task['id']}",
                userId: "${task['assigne']}",
                groupId: "${task['group']}",
                inputVariables: "$variables",
                outputVariables: "${task['outputs']}",
                status: status,
                taskName: "${task['name']}",
                processInstanceId: "${task['process_instance_id']}",
                processDefKey: "${task['process_def_key']}",
                synced: synced,
                taskDefinitionKey: "${task['task_definition_key']}",
        )
        TaskList.withNewTransaction { taskList.save(failOnError: true, flush: true) }
    }

    static def downloadTasks(def uri) {
        def path_end = ''
        def builder = new JsonBuilder()

        //receive from workflow
        try {
            def http = new HTTPBuilder(uri)
            http.headers.Accept = ContentType.JSON
            http.request(Method.GET, ContentType.JSON) {
                response.success = {
                    res, json ->
                        def tasklist = json['tasks']
                        def hasMore = json['hasMore']
                        def nextStart = json['nextStart']

                        //Setting the parser type to JsonParserLax
                        tasklist.each { task ->
                            def returnedVariables = []
                            def variables = task['variables']
                            variables.each { v ->
                                def mapKey = v['key'].toString()
                                def mapValue = v['value'].toString().replace("'", "")
                                def mapType = v['type'].toString()
                                returnedVariables << ['KEY': mapKey, 'VALUE': mapValue, 'TYPE': mapType]
                            }

                            builder {
                                data(returnedVariables.collect { [key: it.KEY, value: it.VALUE, type: it.TYPE] })
                            }

                            saveTaskToDB(task, builder)
                        }

                        if (hasMore == true) {
                            path_end = nextStart + '/50'
                            downloadTasks(url + path + path_end)
                        }
                }
            }
        } catch (Exception e) {
            println "Exception $e"
        }
    }

    def deleteCompletedTask(TaskList task) {
        Archive archive = new Archive()
        archive.taskId = task.taskId
        archive.userId = task.userId
        archive.groupId = task.groupId
        archive.inputVariables = task.inputVariables
        archive.outputVariables = task.outputVariables
        archive.formId = task.formId
        archive.status = task.status
        archive.taskName = task.taskName
        archive.processInstanceId = task.processInstanceId
        archive.processDefKey = task.processDefKey
        archive.synced = task.synced
        archive.taskDefinitionKey = task.taskDefinitionKey
        archive.dateCreated = task.dateCreated
        archive.lastUpdated = task.lastUpdated
        archive.save(flush: true, failOnError: true)
        TaskList.where {synced == 'true' && id == task.id }.deleteAll()
    }

    static def setTaskSyncStatusToTrue(def id) {
        def taskList = TaskList.get(id)
        taskList.synced = 'true'
        taskList.save(failOnError: true, flush: true)
    }

    def sendTasksToWorkflow(TaskList task) {
        def output = '{"taskId": "' + task.taskId + '", "variables": ' + task.outputVariables + ' }'
        // POST
        try {
            def http = new HTTPBuilder(url+'/complete-task')
            http.headers.Accept = ContentType.JSON
            http.request(Method.POST, ContentType.JSON) { req ->
                body = output
                requestContentType = ContentType.JSON
                response.success = { resp, json ->
                    println "Camunda :: receivedOutputVariables() True [ ${json} ]"
                    setTaskSyncStatusToTrue(task.id)
                    this.deleteCompletedTask(task)
                }
                response.failure = { resp ->
                    println "Camunda :: receivedOutputVariables() False [ ${resp.status} ]"
                }
            }
        } catch (Exception e) {
            println "Exception $e"
        }
    }

    @Transactional
    def archiveReport() {
        def query = "SELECT * FROM task_list WHERE status = 'not_started' AND (task_definition_key = 'Archive_Report')"
        def forArchiving = AppHolder.withMisSql { rows(query as String) }
        if (forArchiving.size() > 0) {
            forArchiving.each { task ->
                Archive archive = new Archive()
                archive.taskId = task['task_id']
                archive.inputVariables = task['input_variables']
                archive.outputVariables = task['output_variables']
                archive.status = task['status']
                archive.formId = task['form_id']
                archive.groupId = task['group_id']
                archive.userId = task['user_id']
                archive.taskName = task['task_name']
                archive.processInstanceId = task['process_instance_id']
                archive.processDefKey = task['process_def_key']
                archive.synced = task['synced']
                archive.taskDefinitionKey = task['task_definition_key']
                archive.save(flush: true, failOnError: true)

                //complete the archived task
                TaskList taskList = TaskList.findById(task['id'] as String)
                taskList.status = 'completed'
                taskList.save(flush: true, failOnError: true)
            }
        }
    }

}

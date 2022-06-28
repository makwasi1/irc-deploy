package com.kengamis.tasks

import com.kengamis.Archive
import com.kengamis.TaskList
import groovy.json.JsonBuilder
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

class TaskListSyncJob extends Script {
    static def url = StartCamundaInstancesJob.camundaApiUrl

    @Override
    Object run() {
        downloadTasks(url + '/get-tasks/CRVPF_REPORTING/' + '0/50')
        downloadTasks(url + '/get-tasks/GRANT_PROCESS/' + '0/50')
        downloadTasks(url + '/get-tasks/LONG_TERM_GRANT/' + '0/50')
        //send data to workflow
        def data = TaskList.where { status == 'completed' && synced == 'false' }.findAll()
        data.each { sendTasksToWorkflow(it as TaskList) }

        return null
    }

    static def saveTaskToDB(task, variables) {
        def status = "not_started"
        def synced = "false"

        TaskList.findByTaskId("${task['id']}") ?: new TaskList(
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
        ).save(failOnError: true, flush: true)
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
        archive.inputVariables = task.inputVariables
        archive.outputVariables = task.outputVariables
        archive.status = task.status
        archive.formId = task.formId
        archive.groupId = task.groupId
        archive.userId = task.userId
        archive.taskName = task.taskName
        archive.processInstanceId = task.processInstanceId
        archive.processDefKey = task.processDefKey
        archive.synced = task.synced
        archive.taskDefinitionKey = task.taskDefinitionKey
        archive.save(flush: true, failOnError: true)
        TaskList.where { synced == 'true' && id == task.id }.deleteAll()
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
            def http = new HTTPBuilder(url + '/complete-task')
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

}

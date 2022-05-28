package com.kengamis

class GrantProvideLearningGrant {

    String id
    String grantId

    String grantAmount
    String leadAgency
    String dateFrom
    String dateTo
    String clusterName
    String comments
    String user

    String definitionKey
    String processInstanceId
    String status
    Date dateCreated
    Date lastUpdated

    static constraints = {
        status nullable: true
        user nullable: true
    }

    static mapping = {
        id generator: 'uuid2'
    }
}

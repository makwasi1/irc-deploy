package com.kengamis

class EntityForm {

    String id
    Date	dateCreated
    Date	lastUpdated

    static	belongsTo	= [misEntity: MisEntity, form:Form]
    static	hasMany		= [fieldsMap: EntityFormFieldMap]

    static mapping = {
        id generator: 'uuid2'
    }
    static constraints = {
    }
}

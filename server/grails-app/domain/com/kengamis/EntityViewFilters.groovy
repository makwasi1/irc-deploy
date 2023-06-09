package com.kengamis

class EntityViewFilters {

    String id
    String name
    String description
    String filterQuery
    Date dateCreated
    Date lastUpdated

    static belongsTo = [entityView: EntityView]
    static hasMany = [userEntityViewFilters: UserEntityViewFilters]

    static constraints = {
        filterQuery nullable: true
        description nullable: true
    }

    static mapping = {
        id generator: 'uuid2'
        filterQuery type: 'text'
    }

    /*
    * Methods of the Domain Class
    */
    @Override	// Override toString for a nicer / more descriptive UI
    public String toString() {
        return "${name}"
    }
    static  create(name,filterQuery, entityView) {
        def instance = new EntityViewFilters(name:name, filterQuery: filterQuery, entityView:entityView)
        instance.save(flush: true)
        return  instance
    }

    Set<User> getUsers() {
        (UserEntityViewFilters.findAllByEntityViewFilters(this) as List<UserEntityViewFilters>)*.user as Set<User>
    }
}

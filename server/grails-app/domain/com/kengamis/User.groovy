package com.kengamis

import grails.plugin.springsecurity.SpringSecurityUtils
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

class User {

    transient springSecurityService

    String id
    String names
    String email
    String username
    String password
    boolean enabled = true
    boolean accountExpired = false
    boolean accountLocked = false
    boolean passwordExpired = false
    Date dateCreated
    Date lastUpdated

    static transients = ['springSecurityService']

    static hasMany = [userRoles: UserRole, userEntityViewFilters: UserEntityViewFilters, kengaUserGroup: KengaUserGroup]

    static constraints = {
        password nullable: false, blank: false, password: true
        username nullable: false, blank: false, unique: true
        email nullable: true
    }

    static mapping = {
        id generator: 'uuid2'
        password column: '`password`'
    }

    /*
    * Methods of the Domain Class
    */
    @Override	// Override toString for a nicer / more descriptive UI
    public String toString() {
        return "${names}"
    }

    Set<Role> getAuthorities() {
        (UserRole.findAllByUser(this) as List<UserRole>)*.role as Set<Role>
    }

    ProgramPartner getPartner() {
        def pp = (UserPartner.findByUser(this) as UserPartner)
        pp ? pp.programPartner as ProgramPartner : null
    }

    Set<EntityViewFilters> getEntityViewFilters() {
        (UserEntityViewFilters.findAllByUser(this) as List<UserEntityViewFilters>)*.entityViewFilters as Set<EntityViewFilters>
    }

    Set<KengaGroup> getKengaGroups() {
        (KengaUserGroup.findAllByUser(this) as List<KengaUserGroup>)*.kengaGroup as Set<KengaGroup>
    }

    def roles() {
        return this.userRoles.collect { it.role }
    }

    boolean hasRole(Role r) {
        if (r == null || id == null)
            return false
        return authorities.any { r.authority == it.authority }
    }

    boolean hasAnyRole(String... roleNames) {
        roleNames.any { role -> authorities?.any { role == it.authority } }
    }

    Set<Study> getStudies() {
        UserStudy.findAllByUser(this).collect { it.study } as Set
    }

    Set<KengaGroup> getGroups() {
        UserGroup.findAllByUser(this).collect { it.group } as Set
    }

    Set<Study> getLoggedInUserStudies() {
        if (SpringSecurityUtils.ifAnyGranted('ROLE_SUPER_ADMIN,ROLE_ADMIN')) {
            return Study.list(sort: 'name',order: 'asc') as Set
        } else {
            UserStudy.findAllByUser(this).collect { it.study }.sort {it.name} as Set
        }
    }

    boolean isAdmin(){
        return SpringSecurityUtils.ifAnyGranted('ROLE_SUPER_ADMIN,ROLE_ADMIN')
    }

}

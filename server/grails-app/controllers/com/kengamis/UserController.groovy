package com.kengamis

import grails.converters.JSON
import grails.validation.ValidationException
import org.apache.http.HttpStatus
import org.springframework.web.multipart.MultipartFile

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class UserController {

    UserService userService
    UserRoleService userRoleService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 1000, 1000)
        def role = Role.findByAuthority("ROLE_DATA_COLLECTOR")
        def users = UserRole.findAllByRoleNotEqual(role).collect{
            def roles = it.user.authorities.collect { it.authority }.join(", ")
            def groups = it.user.kengaGroups.collect { it.name }.join(", ")
            [id: it.user.id, username: it.user.username, email: it.user.email, names: it.user.names,
            groups: groups, roles: roles, enabled: it.user.enabled]
        }
        respond users
    }

    def show(String id) {
        def user = User.get(id)
        def kengaGroups = user.kengaGroups.collect{it.id}
        def roles = user.authorities
        def partner = user.getPartner()
        def users= [
                id: user.id,
                username: user.username,
                password: user.password,
                email: user.email,
                names: user.names,
                groups: kengaGroups,
                role: roles,
                position: user.position,
                partner: partner.id,
                enabled: user.enabled
        ]
        respond users
    }

    def getDataCollectors() {
        def role = Role.findByAuthority('ROLE_DATA_COLLECTOR')
        def userRoles = UserRole.findAllByRole(role)
        def dataCollectors = userRoles.collect { userRole ->
            def user = userRole.user
            [names: user.names, id: user.id] }
        respond dataCollectors
    }

    @Transactional
    def save(User user) {
        if (user == null) {
            render status: NOT_FOUND
            return
        }
        if (user.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond user.errors
            return
        }

        try {
            userService.save(user)
        } catch (ValidationException e) {
            respond user.errors
            return
        }

        respond user, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(User user) {
        def userId =user.id
        def userRole = params.role as String

        if (user == null) {
            render status: NOT_FOUND
            return
        }
        if (user.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond user.errors
            return
        }

        try {
            updateRolesAndGroups(userId, userRole)
            userService.save(user)
        } catch (ValidationException e) {
            respond user.errors
            return
        }

        respond user, [status: OK, view:"show"]
    }

    @Transactional
    def delete(String id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        userService.delete(id)

        render status: NO_CONTENT
    }

    @Transactional
    def uploadUsers() {
        println("===================")
        try{
            MultipartFile file = request.getFile('users')
            String fileType = file.getContentType()
            println fileType
            userService.importUsers(file)
            render([code: HttpStatus.SC_OK, msg: "Successfully uploaded users."] as JSON)
        }catch(Exception ex) {
            ex.printStackTrace()
        }
    }

    @Transactional
    def updateRolesAndGroups(userId, userRole){
        def currentUser = User.get(userId)
        def currentRole = Role.get(userRole)

        UserRole.deleteOldRecords(currentUser)
        KengaUserGroup.deleteOldRecordsUser(currentUser)

//        def usersRole = params.roles as String
        UserRole.create(currentUser, currentRole, true)

        def userGroup = params.groups as String
        def listOfUserGroups = userGroup ? userGroup.split(",") : []
        listOfUserGroups?.each{ myUserGroup ->
            def currentGroup = KengaGroup.get(myUserGroup)
            KengaUserGroup.create(currentGroup,currentUser, true)
        }
    }
}

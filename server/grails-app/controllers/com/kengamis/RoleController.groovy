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
class RoleController {

    RoleService roleService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 1000, 1000)
        respond roleService.list(params), model:[roleCount: roleService.count()]
    }

    def show(String id) {
        respond roleService.get(id)
    }

    def getUserRoles() {
        def username = params.username as String
        def user = User.findByUsername(username)
        def roles = user.authorities.collect { it.authority }
        respond roles
    }

    @Transactional
    def save(Role role) {
        if (role == null) {
            render status: NOT_FOUND
            return
        }
        if (role.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond role.errors
            return
        }

        try {
            roleService.save(role)
        } catch (ValidationException e) {
            respond role.errors
            return
        }

        respond role, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Role role) {
        if (role == null) {
            render status: NOT_FOUND
            return
        }
        if (role.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond role.errors
            return
        }

        try {
            roleService.save(role)
        } catch (ValidationException e) {
            respond role.errors
            return
        }

        respond role, [status: OK, view:"show"]
    }

    @Transactional
    def delete(String id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        roleService.delete(id)

        render status: NO_CONTENT
    }
}

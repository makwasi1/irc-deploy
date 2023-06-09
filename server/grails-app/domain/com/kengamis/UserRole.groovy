package com.kengamis


import grails.gorm.DetachedCriteria
import groovy.transform.ToString
import org.codehaus.groovy.util.HashCodeHelper

@ToString(cache=true, includeNames=true, includePackage=false)
class UserRole implements Serializable {

	private static final long serialVersionUID = 1

	String id
    User user
    Role role

	Date dateCreated
	Date lastUpdated
	@Override
	boolean equals(other) {
		if (other instanceof UserRole) {
			other.userId == user?.id && other.roleId == role?.id
		}
	}

    @Override
	int hashCode() {
	    int hashCode = HashCodeHelper.initHash()
        if (user) {
            hashCode = HashCodeHelper.updateHash(hashCode, user.id)
		}
		if (role) {
		    hashCode = HashCodeHelper.updateHash(hashCode, role.id)
		}
		hashCode
	}

	static UserRole get(String userId, String roleId) {
		criteriaFor(userId, roleId).get()
	}

	static boolean exists(String userId, String roleId) {
		criteriaFor(userId, roleId).count()
	}

	private static DetachedCriteria criteriaFor(String userId, String roleId) {
		where {
			user == User.load(userId) &&
			role == Role.load(roleId)
		}
	}

	static UserRole create(User user, Role role, boolean flush = false) {
		def instance = new UserRole(user: user, role: role)
		instance.save(flush: flush)
		instance
	}

	static boolean remove(User u, Role r) {
		if (u != null && r != null) {
			where { user == u && role == r }.deleteAll()
		}
	}

	static int removeAll(User u) {
		u == null ? 0 : where { user == u }.deleteAll() as int
	}

	static int removeAll(Role r) {
		r == null ? 0 : where { role == r }.deleteAll() as int
	}

	static void deleteOldRecords(User e){
		def filters = findAllByUser(e)
		filters.each { it.delete(flush: true, failOnError: true) }
	}

	static constraints = {
	    user nullable: false
		role nullable: false, validator: { Role r, UserRole ur ->
			if (ur.user?.id) {
				if (exists(ur.user.id, r.id)) {
				    return ['userRole.exists']
				}
			}
		}
	}

	static mapping = {
		id generator: 'uuid2'
		id composite: ['user', 'role']
		//version false
	}

	static belongsTo = [user: User, role: Role]
}

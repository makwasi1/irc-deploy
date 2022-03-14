package com.kengamis.acl

import com.kengamis.KengaGroup

class KengaGroupAclEntry {
    KengaAclTableRecordIdentity kengaAclTableRecordIdentity
    //any user with this group automatically gets the permissions over the record
    KengaGroup kengaGroup
//    mask determines permission, A value of 1 indicates permission A,
//    a value of 2 indicates permission B, a value of 4 indicates permission C,
//    a value of 8 indicates permission D, etc
    int mask
//    the order of the permission
    int aceOrder
    static constraints = {
        kengaAclTableRecordIdentity nullable: false
        kengaGroup nullable: false
    }
}
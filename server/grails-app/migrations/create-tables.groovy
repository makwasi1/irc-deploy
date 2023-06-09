databaseChangeLog = {
    changeSet(author: "Bryan (generated)", id: "1638949600872-1") {
        createTable(tableName: "choice_option") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "choice_optionPK")
            }

            column(name: "choice_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "form_setting_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "text", type: "LONGTEXT") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Bryan (generated)", id: "1638949600872-2") {
        createTable(tableName: "form") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "formPK")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "study_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "central_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "display_name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "sync_mode", type: "BIT") {
                constraints(nullable: "false")
            }

            column(name: "enabled", type: "BIT") {
                constraints(nullable: "false")
            }

            column(name: "description", type: "VARCHAR(255)")
        }
    }

    changeSet(author: "Bryan (generated)", id: "1638949600872-3") {
        createTable(tableName: "form_setting") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "form_settingPK")
            }

            column(name: "question_text", type: "LONGTEXT")

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "type_of_question", type: "VARCHAR(12)") {
                constraints(nullable: "false")
            }

            column(name: "parent_question", type: "VARCHAR(255)")

            column(name: "filter_by_text", type: "BIT") {
                constraints(nullable: "false")
            }

            column(name: "xform_type", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "display_name", type: "LONGTEXT")

            column(name: "form_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "order_of_display_in_table", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "view_on_map", type: "BIT") {
                constraints(nullable: "false")
            }

            column(name: "field", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "add_to_filter", type: "BIT") {
                constraints(nullable: "false")
            }

            column(name: "view_in_table", type: "BIT") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Bryan (generated)", id: "1638949600872-4") {
        createTable(tableName: "group") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "groupPK")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Bryan (generated)", id: "1638949600872-5") {
        createTable(tableName: "request_map") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "request_mapPK")
            }

            column(name: "http_method", type: "VARCHAR(255)")

            column(name: "config_attribute", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "url", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Bryan (generated)", id: "1638949600872-6") {
        createTable(tableName: "role") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "rolePK")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "authority", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Bryan (generated)", id: "1638949600872-7") {
        createTable(tableName: "study") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "studyPK")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "sync_to_metabase", type: "BIT") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "archive_study", type: "BIT") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "sync_mode", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "central_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Bryan (generated)", id: "1638949600872-8") {
        createTable(tableName: "task_def") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "task_defPK")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "start_on_startup", type: "BIT") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "extra_params", type: "VARCHAR(255)")

            column(name: "cron_expression", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "description", type: "VARCHAR(255)")

            column(name: "task_class", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Bryan (generated)", id: "1638949600872-9") {
        createTable(tableName: "user") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "userPK")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "password_expired", type: "BIT") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "account_expired", type: "BIT") {
                constraints(nullable: "false")
            }

            column(name: "names", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "username", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "account_locked", type: "BIT") {
                constraints(nullable: "false")
            }

            column(name: "password", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "enabled", type: "BIT") {
                constraints(nullable: "false")
            }

            column(name: "email", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Bryan (generated)", id: "1638949600872-10") {
        createTable(tableName: "user_form") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "user_formPK")
            }

            column(name: "form_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "user_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Bryan (generated)", id: "1638949600872-11") {
        createTable(tableName: "user_group") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "user_groupPK")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "user_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "group_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "group_role", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Bryan (generated)", id: "1638949600872-12") {
        createTable(tableName: "user_role") {
            column(name: "user_id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "user_rolePK")
            }

            column(name: "role_id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "user_rolePK")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Bryan (generated)", id: "1638949600872-13") {
        createTable(tableName: "user_study") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "user_studyPK")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "user_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "study_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Bryan (generated)", id: "1638949600872-14") {
        addUniqueConstraint(columnNames: "name", constraintName: "UC_GROUPNAME_COL", tableName: "group")
    }

    changeSet(author: "Bryan (generated)", id: "1638949600872-15") {
        addUniqueConstraint(columnNames: "authority", constraintName: "UC_ROLEAUTHORITY_COL", tableName: "role")
    }

    changeSet(author: "Bryan (generated)", id: "1638949600872-16") {
        addUniqueConstraint(columnNames: "name", constraintName: "UC_TASK_DEFNAME_COL", tableName: "task_def")
    }

    changeSet(author: "Bryan (generated)", id: "1638949600872-17") {
        addUniqueConstraint(columnNames: "username", constraintName: "UC_USERUSERNAME_COL", tableName: "user")
    }

    changeSet(author: "Bryan (generated)", id: "1638949600872-18") {
        addUniqueConstraint(columnNames: "group_id, user_id", constraintName: "UK30e727c77fe15a65e4252a73b988", tableName: "user_group")
    }

    changeSet(author: "Bryan (generated)", id: "1638949600872-19") {
        addUniqueConstraint(columnNames: "study_id, user_id", constraintName: "UK9ecfee57007fc93135c5b06bf23a", tableName: "user_study")
    }

    changeSet(author: "Bryan (generated)", id: "1638949600872-20") {
        addUniqueConstraint(columnNames: "form_id, user_id", constraintName: "UKd512f11de5e919c52773dbc40494", tableName: "user_form")
    }

    changeSet(author: "Bryan (generated)", id: "1638949600872-21") {
        addUniqueConstraint(columnNames: "http_method, url", constraintName: "UKf721bf1f2340334e273dd57aedcb", tableName: "request_map")
    }

    changeSet(author: "Bryan (generated)", id: "1638949600872-22") {
        addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "user_group", constraintName: "FK1c1dsw3q36679vaiqwvtv36a6", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", validate: "true")
    }

    changeSet(author: "Bryan (generated)", id: "1638949600872-23") {
        addForeignKeyConstraint(baseColumnNames: "form_setting_id", baseTableName: "choice_option", constraintName: "FK6ulbwgetjfe77f1qg84bxm0qx", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "form_setting", validate: "true")
    }

    changeSet(author: "Bryan (generated)", id: "1638949600872-24") {
        addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "user_role", constraintName: "FK859n2jvi8ivhui0rl0esws6o", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", validate: "true")
    }

    changeSet(author: "Bryan (generated)", id: "1638949600872-25") {
        addForeignKeyConstraint(baseColumnNames: "study_id", baseTableName: "user_study", constraintName: "FK8g3qtmfhqft80t854j2n2gawm", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "study", validate: "true")
    }

    changeSet(author: "Bryan (generated)", id: "1638949600872-26") {
        addForeignKeyConstraint(baseColumnNames: "role_id", baseTableName: "user_role", constraintName: "FKa68196081fvovjhkek5m97n3y", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "role", validate: "true")
    }

    changeSet(author: "Bryan (generated)", id: "1638949600872-27") {
        addForeignKeyConstraint(baseColumnNames: "form_id", baseTableName: "user_form", constraintName: "FKet5bou1rejvasjlchtk95m0f", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "form", validate: "true")
    }

    changeSet(author: "Bryan (generated)", id: "1638949600872-28") {
        addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "user_study", constraintName: "FKguhhymf5vvsah78agbjdgc3jp", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", validate: "true")
    }

    changeSet(author: "Bryan (generated)", id: "1638949600872-29") {
        addForeignKeyConstraint(baseColumnNames: "form_id", baseTableName: "form_setting", constraintName: "FKiy86i2kxa61thdtihfuat58gs", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "form", validate: "true")
    }

    changeSet(author: "Bryan (generated)", id: "1638949600872-30") {
        addForeignKeyConstraint(baseColumnNames: "group_id", baseTableName: "user_group", constraintName: "FKjonf4pqux3h1e687sd18dhcnj", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "group", validate: "true")
    }

    changeSet(author: "Bryan (generated)", id: "1638949600872-31") {
        addForeignKeyConstraint(baseColumnNames: "study_id", baseTableName: "form", constraintName: "FKl3mw5v1w4i5goeqd42v88srw3", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "study", validate: "true")
    }

    changeSet(author: "Bryan (generated)", id: "1638949600872-32") {
        addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "user_form", constraintName: "FKm04eqjlt7jll97dnmhk01dsh8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", validate: "true")
    }

    changeSet(author: "Bryan (generated)", id: "1639378731012-1") {
        createTable(tableName: "entity_fields") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "entity_fieldsPK")
            }

            column(name: "sql_data_type", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "display_name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "data_type", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "field_type", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "mandatory", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "field_name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "order_of_display", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "filter_order", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "mis_entity_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Bryan (generated)", id: "1639378731012-2") {
        createTable(tableName: "entity_form") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "entity_formPK")
            }

            column(name: "form_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "mis_entity_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Bryan (generated)", id: "1639378731012-3") {
        createTable(tableName: "entity_form_field_map") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "entity_form_field_mapPK")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "entity_field", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "entity_form_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "form_field", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Bryan (generated)", id: "1639378731012-4") {
        createTable(tableName: "mis_entity") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "mis_entityPK")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "table_name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "ignore_user_context", type: "BIT") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Bryan (generated)", id: "1639378731012-5") {
        addForeignKeyConstraint(baseColumnNames: "entity_form_id", baseTableName: "entity_form_field_map", constraintName: "FKbfltmac8n7vlvdc2f2fh17mi3", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "entity_form", validate: "true")
    }

    changeSet(author: "Bryan (generated)", id: "1639378731012-6") {
        addForeignKeyConstraint(baseColumnNames: "mis_entity_id", baseTableName: "entity_fields", constraintName: "FKl2rp5prj1b840d9rbmkmp8f5w", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "mis_entity", validate: "true")
    }

    changeSet(author: "Bryan (generated)", id: "1639378731012-7") {
        addForeignKeyConstraint(baseColumnNames: "mis_entity_id", baseTableName: "entity_form", constraintName: "FKqno4734ea6k0mqcgj2fqnppoq", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "mis_entity", validate: "true")
    }

    changeSet(author: "Bryan (generated)", id: "1639378731012-8") {
        addForeignKeyConstraint(baseColumnNames: "form_id", baseTableName: "entity_form", constraintName: "FKso5r8nh2ph67ufqlmdyhe7yjq", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "form", validate: "true")
    }

    changeSet(author: "Bryan (generated)", id: "1641206080681-1") {
        addColumn(tableName: "entity_form_field_map") {
            column(name: "entity_field_type", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Bryan (generated)", id: "1641206080681-2") {
        addColumn(tableName: "entity_form_field_map") {
            column(name: "form_field_type", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Bryan (generated)", id: "1641561324661-1") {
        createTable(tableName: "entity_view") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "entity_viewPK")
            }

            column(name: "view_query", type: "LONGTEXT")

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "table_name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "ignore_user_context", type: "BIT") {
                constraints(nullable: "false")
            }

            column(name: "description", type: "VARCHAR(255)")

            column(name: "mis_entity_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Bryan (generated)", id: "1641561324661-2") {
        createTable(tableName: "entity_view_fields") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "entity_view_fieldsPK")
            }

            column(name: "datatype", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "field_type", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "entity_view_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "order_of_display", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "filter_order", type: "INT") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Bryan (generated)", id: "1641561324661-3") {
        addForeignKeyConstraint(baseColumnNames: "mis_entity_id", baseTableName: "entity_view", constraintName: "FK1ntgwta5w3svsh0yhy2f035xk", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "mis_entity", validate: "true")
    }

    changeSet(author: "Bryan (generated)", id: "1641561324661-4") {
        addForeignKeyConstraint(baseColumnNames: "entity_view_id", baseTableName: "entity_view_fields", constraintName: "FKio7hqe8r4oyua9qfs7g5sbmar", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "entity_view", validate: "true")
    }

    changeSet(author: "BrunoJay (generated)", id: "1641542082472-1") {
        createTable(tableName: "task_list") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "task_listPK")
            }

            column(name: "user_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "group_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "output_variables", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "process_def_key", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "input_variables", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "synced", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "form_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "task_name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "process_instance_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "status", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "task_definition_key", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "BrunoJay (generated)", id: "1641553977460-1") {
        addColumn(tableName: "task_list") {
            column(name: "task_id", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "BrunoJay (generated)", id: "1641554948339-1") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "form_id", tableName: "task_list")
    }

    changeSet(author: "Bryan (generated)", id: "1644499803165-1") {
        createTable(tableName: "tag") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "tagPK")
            }

            column(name: "tag_type_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Bryan (generated)", id: "1644499803165-2") {
        createTable(tableName: "tag_type") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "tag_typePK")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Bryan (generated)", id: "1644499803165-3") {
        addForeignKeyConstraint(baseColumnNames: "tag_type_id", baseTableName: "tag", constraintName: "FK4dal6a59cl5t8omhsi2yrtk0g", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "tag_type", validate: "true")
    }

    changeSet(author: "BrunoJay (generated)", id: "1644910564036-1") {
        createTable(tableName: "report_form") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "report_formPK")
            }

            column(name: "process_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "user_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "group_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "report_values", type: "text") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "task_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "status", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "task_definition_key", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "BrunoJay (generated)", id: "1644910564036-2") {
        createTable(tableName: "report_form_comments") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "report_form_commentsPK")
            }

            column(name: "process_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "user_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "group_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "task_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "content", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "children", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "task_definition_key", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "BrunoJay (generated)", id: "1644910564036-3") {
        createTable(tableName: "report_form_files") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "report_form_filesPK")
            }

            column(name: "process_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "user_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "group_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "path", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "task_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "task_definition_key", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "BrunoJay (generated)", id: "1644910564036-4") {
        createTable(tableName: "report_form_recommendations") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "report_form_recommendationsPK")
            }

            column(name: "process_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "user_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "group_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "task_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "content", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "task_definition_key", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Bryan (generated)", id: "1644824111661-1") {
        addColumn(tableName: "tag_type") {
            column(name: "mis_entity_id", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Bryan (generated)", id: "1644824111661-2") {
        addForeignKeyConstraint(baseColumnNames: "mis_entity_id", baseTableName: "tag_type", constraintName: "FK2yamjvvmhg3koumfynkxt9ail", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "mis_entity", validate: "true")
    }

    changeSet(author: "Bryan (generated)", id: "1644832502002-1") {
        addColumn(tableName: "mis_entity") {
            column(name: "enable_tagging", type: "bit") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Bryan (generated)", id: "1644832502002-2") {
        addColumn(tableName: "mis_entity") {
            column(name: "prefix", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Bryan (generated)", id: "1644847113497-1") {
        addColumn(tableName: "mis_entity") {
            column(name: "prefix_increment_table", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Bryan (generated)", id: "1644911791342-1") {
        addColumn(tableName: "mis_entity") {
            column(name: "entity_tag_table", type: "varchar(255)")
        }
    }

    changeSet(author: "victorkakama (generated)", id: "1645511152009-1") {
        createTable(tableName: "acl_class") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "acl_classPK")
            }

            column(name: "class", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "victorkakama (generated)", id: "1645511152009-2") {
        createTable(tableName: "acl_entry") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "acl_entryPK")
            }

            column(name: "sid", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "audit_failure", type: "BIT") {
                constraints(nullable: "false")
            }

            column(name: "granting", type: "BIT") {
                constraints(nullable: "false")
            }

            column(name: "acl_object_identity", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "audit_success", type: "BIT") {
                constraints(nullable: "false")
            }

            column(name: "ace_order", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "mask", type: "INT") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "victorkakama (generated)", id: "1645511152009-3") {
        createTable(tableName: "acl_object_identity") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "acl_object_identityPK")
            }

            column(name: "object_id_identity", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "entries_inheriting", type: "BIT") {
                constraints(nullable: "false")
            }

            column(name: "object_id_class", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "owner_sid", type: "BIGINT")

            column(name: "parent_object", type: "BIGINT")
        }
    }

    changeSet(author: "victorkakama (generated)", id: "1645511152009-4") {
        createTable(tableName: "acl_sid") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "acl_sidPK")
            }

            column(name: "sid", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "principal", type: "BIT") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "victorkakama (generated)", id: "1645511152009-5") {
        addUniqueConstraint(columnNames: "class", constraintName: "UC_ACL_CLASSCLASS_COL", tableName: "acl_class")
    }

    changeSet(author: "victorkakama (generated)", id: "1645511152009-6") {
        addUniqueConstraint(columnNames: "sid, principal", constraintName: "UK1781b9a084dff171b580608b3640", tableName: "acl_sid")
    }

    changeSet(author: "victorkakama (generated)", id: "1645511152009-7") {
        addUniqueConstraint(columnNames: "object_id_class, object_id_identity", constraintName: "UK56103a82abb455394f8c97a95587", tableName: "acl_object_identity")
    }

    changeSet(author: "victorkakama (generated)", id: "1645511152009-8") {
        addUniqueConstraint(columnNames: "acl_object_identity, ace_order", constraintName: "UKce200ed06800e5a163c6ab6c0c85", tableName: "acl_entry")
    }

    changeSet(author: "victorkakama (generated)", id: "1645511152009-9") {
        addForeignKeyConstraint(baseColumnNames: "parent_object", baseTableName: "acl_object_identity", constraintName: "FK4soxn7uid8qxltqps8kewftx7", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "acl_object_identity", validate: "true")
    }

    changeSet(author: "victorkakama (generated)", id: "1645511152009-10") {
        addForeignKeyConstraint(baseColumnNames: "sid", baseTableName: "acl_entry", constraintName: "FK9r4mj8ewa904g3wivff0tb5b0", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "acl_sid", validate: "true")
    }

    changeSet(author: "victorkakama (generated)", id: "1645511152009-11") {
        addForeignKeyConstraint(baseColumnNames: "object_id_class", baseTableName: "acl_object_identity", constraintName: "FKc06nv93ck19el45a3g1p0e58w", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "acl_class", validate: "true")
    }

    changeSet(author: "victorkakama (generated)", id: "1645511152009-12") {
        addForeignKeyConstraint(baseColumnNames: "owner_sid", baseTableName: "acl_object_identity", constraintName: "FKikrbtok3aqlrp9wbq6slh9mcw", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "acl_sid", validate: "true")
    }

    changeSet(author: "victorkakama (generated)", id: "1645511152009-13") {
        addForeignKeyConstraint(baseColumnNames: "acl_object_identity", baseTableName: "acl_entry", constraintName: "FKl39t1oqikardwghegxe0wdcpt", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "acl_object_identity", validate: "true")
    }

    changeSet(author: "victorkakama (generated)", id: "1645697783843-2") {
        createTable(tableName: "kenga_group") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "kenga_groupPK")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "victorkakama (generated)", id: "1645697783843-3") {
        addUniqueConstraint(columnNames: "name", constraintName: "UC_KENGA_GROUPNAME_COL", tableName: "kenga_group")
    }

    changeSet(author: "victorkakama (generated)", id: "1645697783843-4") {
        addForeignKeyConstraint(baseColumnNames: "group_id", baseTableName: "user_group", constraintName: "FKk2k4e53v15rb9374r2nu24rts", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "kenga_group", validate: "true")
    }

    changeSet(author: "victorkakama (generated)", id: "1645697783843-5") {
        dropForeignKeyConstraint(baseTableName: "user_group", constraintName: "FKjonf4pqux3h1e687sd18dhcnj")
    }
    changeSet(author: "victorkakama (generated)", id: "1645697783843-8") {
        dropUniqueConstraint(constraintName: "UC_GROUPNAME_COL", tableName: "group")
    }
    changeSet(author: "victorkakama (generated)", id: "1646033595933-1") {
        createTable(tableName: "kenga_group_role") {
            column(name: "kenga_group_id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "kenga_group_rolePK")
            }

            column(name: "role_id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "kenga_group_rolePK")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "victorkakama (generated)", id: "1646033595933-2") {
        addForeignKeyConstraint(baseColumnNames: "kenga_group_id", baseTableName: "kenga_group_role", constraintName: "FK6j6xb1j0i5wp1b50f7ff7f39u", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "kenga_group", validate: "true")
    }

    changeSet(author: "victorkakama (generated)", id: "1646033595933-3") {
        addForeignKeyConstraint(baseColumnNames: "role_id", baseTableName: "kenga_group_role", constraintName: "FK8fmsx15k5wgah0nxu3aojreiy", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "role", validate: "true")
    }

    changeSet(author: "victorkakama (generated)", id: "1646033595933-4") {
        dropTable(tableName: "group")
    }

    changeSet(author: "victorkakama (generated)", id: "1646033595933-5") {
        dropColumn(columnName: "group_role", tableName: "user_group")
    }

    changeSet(author: "victorkakama (generated)", id: "1646915942170-1") {
        createTable(tableName: "kenga_acl_table_record_identity") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "kenga_acl_table_record_identityPK")
            }

            column(name: "data_table_record_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "kenga_data_table_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "kenga_group_id", type: "VARCHAR(255)")
        }
    }

    changeSet(author: "victorkakama (generated)", id: "1646915942170-2") {
        createTable(tableName: "kenga_data_table") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "kenga_data_tablePK")
            }

            column(name: "table_name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "victorkakama (generated)", id: "1646915942170-3") {
        createTable(tableName: "kenga_group_acl_entry") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "kenga_group_acl_entryPK")
            }

            column(name: "kenga_acl_table_record_identity_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "kenga_group_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "ace_order", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "mask", type: "INT") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "victorkakama (generated)", id: "1646915942170-4") {
        createTable(tableName: "kenga_user_group") {
            column(name: "kenga_group_id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "kenga_user_groupPK")
            }

            column(name: "user_id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "kenga_user_groupPK")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "victorkakama (generated)", id: "1646915942170-5") {
        addUniqueConstraint(columnNames: "table_name", constraintName: "UC_KENGA_DATA_TABLETABLE_NAME_COL", tableName: "kenga_data_table")
    }

    changeSet(author: "victorkakama (generated)", id: "1645520788547-2") {
        addColumn(tableName: "role") {
            column(name: "description", type: "varchar(255)")
        }
    }
    changeSet(author: "victorkakama (generated)", id: "1645520788547-1") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "email", tableName: "user")
    }
    changeSet(author: "victorkakama (generated)", id: "1645545375346-1") {
        dropForeignKeyConstraint(baseTableName: "user_form", constraintName: "FKet5bou1rejvasjlchtk95m0f")
    }
    changeSet(author: "victorkakama (generated)", id: "1645545375346-2") {
        dropForeignKeyConstraint(baseTableName: "user_form", constraintName: "FKm04eqjlt7jll97dnmhk01dsh8")
    }
    changeSet(author: "victorkakama (generated)", id: "1645545375346-5") {
        dropUniqueConstraint(constraintName: "UKd512f11de5e919c52773dbc40494", tableName: "user_form")
    }
    changeSet(author: "victorkakama (generated)", id: "1645545375346-10") {
        dropTable(tableName: "user_form")
    }

    changeSet(author: "Bryan (generated)", id: "1646229444195-1") {
        createTable(tableName: "project_milestone") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "project_milestonePK")
            }

            column(name: "reporting_query", type: "LONGTEXT")

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "dashboard_table", type: "VARCHAR(255)")

            column(name: "dashboard_query", type: "LONGTEXT")

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "reporting_table", type: "VARCHAR(255)")

            column(name: "description", type: "VARCHAR(255)")
        }
    }

    changeSet(author: "Bryan (generated)", id: "1646313846384-1") {
        createTable(tableName: "program") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "programPK")
            }

            column(name: "title", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "description", type: "VARCHAR(255)")
        }
    }

    changeSet(author: "Bryan (generated)", id: "1646313846384-2") {
        createTable(tableName: "program_category") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "program_categoryPK")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "description", type: "VARCHAR(255)")

            column(name: "program_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Bryan (generated)", id: "1646313846384-3") {
        addColumn(tableName: "project_milestone") {
            column(name: "program", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Bryan (generated)", id: "1646313846384-4") {
        addColumn(tableName: "project_milestone") {
            column(name: "program_category_id", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Bryan (generated)", id: "1646313846384-5") {
        addForeignKeyConstraint(baseColumnNames: "program_id", baseTableName: "program_category", constraintName: "FK1wjnxstqhnlgc5bkgf4so28d7", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "program", validate: "true")
    }

    changeSet(author: "Bryan (generated)", id: "1646313846384-6") {
        addForeignKeyConstraint(baseColumnNames: "program_category_id", baseTableName: "project_milestone", constraintName: "FKlltcynbquxvrpdr5lrrk19gwp", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "program_category", validate: "true")
    }


    changeSet(author: "Bryan (generated)", id: "1646375252167-1") {
        createTable(tableName: "entity_view_filters") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "entity_view_filtersPK")
            }

            column(name: "entity_view_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "filter_query", type: "LONGTEXT")

            column(name: "description", type: "VARCHAR(255)")
        }
    }

    changeSet(author: "Bryan (generated)", id: "1646375252167-2") {
        addForeignKeyConstraint(baseColumnNames: "entity_view_id", baseTableName: "entity_view_filters", constraintName: "FKccsoafxljte0s32kxgj58sy58", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "entity_view", validate: "true")
    }

    changeSet(author: "Bryan (generated)", id: "1646382998928-1") {
        createTable(tableName: "program_partner") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "program_partnerPK")
            }

            column(name: "legal", type: "VARCHAR(255)")

            column(name: "lead_cluster", type: "VARCHAR(255)")

            column(name: "organisation", type: "VARCHAR(255)")

            column(name: "postal_address", type: "VARCHAR(255)")

            column(name: "physical_address", type: "VARCHAR(255)")

            column(name: "name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "city", type: "VARCHAR(255)")

            column(name: "name_contact_person", type: "VARCHAR(255)")

            column(name: "country", type: "VARCHAR(255)")

            column(name: "email", type: "VARCHAR(255)")

            column(name: "website", type: "VARCHAR(255)")

            column(name: "program_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "acronym", type: "VARCHAR(255)")

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Bryan (generated)", id: "1646382998928-2") {
        addForeignKeyConstraint(baseColumnNames: "program_id", baseTableName: "program_partner", constraintName: "FK2rc72b856yntd7xpsdrj8l0qa", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "program", validate: "true")
    }

    changeSet(author: "BrunoJay (generated)", id: "1646309130239-1") {
        createTable(tableName: "partner_setup") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "partner_setupPK")
            }

            column(name: "setup_values", type: "text") {
                constraints(nullable: "false")
            }

            column(name: "user_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "partner_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "LENOVO (generated)", id: "1646838666964-1") {
        createTable(tableName: "data_view") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "data_viewPK")
            }

            column(name: "view_query", type: "LONGTEXT")

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "table_name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "ignore_user_context", type: "BIT") {
                constraints(nullable: "false")
            }

            column(name: "description", type: "VARCHAR(255)")
        }
    }

    changeSet(author: "victorkakama (generated)", id: "1646915942170-6") {
        addForeignKeyConstraint(baseColumnNames: "kenga_group_id", baseTableName: "kenga_acl_table_record_identity", constraintName: "FK6ftxfqhndokh2opqomfxas4jw", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "kenga_group", validate: "true")
    }

    changeSet(author: "victorkakama (generated)", id: "1646915942170-7") {
        addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "kenga_user_group", constraintName: "FKfyp4kgxa9wq5bhqrmupeo0gel", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", validate: "true")
    }

    changeSet(author: "victorkakama (generated)", id: "1646915942170-8") {
        addForeignKeyConstraint(baseColumnNames: "kenga_acl_table_record_identity_id", baseTableName: "kenga_group_acl_entry", constraintName: "FKgaqw7dig1l625teoqbl0v70x8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "kenga_acl_table_record_identity", validate: "true")
    }

    changeSet(author: "victorkakama (generated)", id: "1646915942170-9") {
        addForeignKeyConstraint(baseColumnNames: "kenga_data_table_id", baseTableName: "kenga_acl_table_record_identity", constraintName: "FKhr0nxosgu06xcby1pifmbp8dj", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "kenga_data_table", validate: "true")
    }

    changeSet(author: "victorkakama (generated)", id: "1646915942170-10") {
        addForeignKeyConstraint(baseColumnNames: "kenga_group_id", baseTableName: "kenga_group_acl_entry", constraintName: "FKjq24i33cpngvs0aamferxunvi", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "kenga_group", validate: "true")
    }

    changeSet(author: "victorkakama (generated)", id: "1646915942170-11") {
        addForeignKeyConstraint(baseColumnNames: "kenga_group_id", baseTableName: "kenga_user_group", constraintName: "FKox52rpnuevjm4m048an1pmxwh", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "kenga_group", validate: "true")
    }

    changeSet(author: "victorkakama (generated)", id: "1646915942170-12") {
        dropForeignKeyConstraint(baseTableName: "kenga_group_role", constraintName: "FK6j6xb1j0i5wp1b50f7ff7f39u")
    }

    changeSet(author: "victorkakama (generated)", id: "1646915942170-13") {
        dropForeignKeyConstraint(baseTableName: "kenga_group_role", constraintName: "FK8fmsx15k5wgah0nxu3aojreiy")
    }
    changeSet(author: "victorkakama (generated)", id: "1646915942170-32") {
        dropTable(tableName: "kenga_group_role")
    }
    changeSet(author: "victorkakama (generated)", id: "1646989014226-1") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "email", tableName: "user")
    }

    changeSet(author: "victorkakama (generated)", id: "1646989014226-2") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "names", tableName: "user")
    }
    changeSet(author: "bruno (generated)", id: "1647339947546-1") {
        addColumn(tableName: "partner_setup") {
            column(name: "end_date", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "1647339947546-2") {
        addColumn(tableName: "partner_setup") {
            column(name: "period_type", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "1647339947546-3") {
        addColumn(tableName: "partner_setup") {
            column(name: "program_id", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "1647339947546-4") {
        addColumn(tableName: "partner_setup") {
            column(name: "reporting_calendar", type: "text") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "1647339947546-5") {
        addColumn(tableName: "partner_setup") {
            column(name: "reporting_start_date", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "1647339947546-6") {
        addColumn(tableName: "partner_setup") {
            column(name: "start_date", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "20220901124635-01") {
        modifyDataType(columnName: "input_variables", newDataType: "LONGTEXT", tableName: "task_list")
    }

    changeSet(author: "bruno (generated)", id: "202209011224245-01") {
        modifyDataType(columnName: "output_variables", newDataType: "LONGTEXT", tableName: "task_list")
    }

    changeSet(author: "victorkakama (generated)", id: "1647936752765-1") {
        addColumn(tableName: "kenga_data_table") {
            column(name: "id_label", type: "varchar(255)")
        }
    }

    changeSet(author: "LENOVO (generated)", id: "1646838666964-1") {
        createTable(tableName: "data_view") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "data_viewPK")
            }

            column(name: "view_query", type: "LONGTEXT")

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "table_name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "ignore_user_context", type: "BIT") {
                constraints(nullable: "false")
            }

            column(name: "description", type: "VARCHAR(255)")
        }
    }

    changeSet(author: "LENOVO (generated)", id: "1647253541462-1") {
        addColumn(tableName: "entity_view_filters") {
            column(name: "user_id", type: "varchar(255)")
        }
    }

    changeSet(author: "LENOVO (generated)", id: "1647253541462-2") {
        addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "entity_view_filters", constraintName: "FK9432frmitk8mc7gfm3h6gqw9d", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", validate: "true")
    }

    changeSet(author: "LENOVO (generated)", id: "1647253541462-2") {
        addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "entity_view_filters", constraintName: "FK9432frmitk8mc7gfm3h6gqw9d", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", validate: "true")
    }

    changeSet(author: "Bryan (generated)", id: "1647848110366-1") {
        createTable(tableName: "user_entity_view_filters") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "user_entity_view_filtersPK")
            }

            column(name: "entity_view_filters_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "user_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Bryan (generated)", id: "1647848110366-2") {
        addColumn(tableName: "entity_view_filters") {
            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Bryan (generated)", id: "1647848110366-3") {
        addColumn(tableName: "entity_view_filters") {
            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Bryan (generated)", id: "1647848110366-4") {
        addUniqueConstraint(columnNames: "entity_view_filters_id, user_id", constraintName: "UK28e93280ffd3817340e71f819e6b", tableName: "user_entity_view_filters")
    }

    changeSet(author: "Bryan (generated)", id: "1647848110366-5") {
        addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "user_entity_view_filters", constraintName: "FK660sr1x5pypub18iwh087qrc2", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", validate: "true")
    }

    changeSet(author: "Bryan (generated)", id: "1647848110366-6") {
        addForeignKeyConstraint(baseColumnNames: "entity_view_filters_id", baseTableName: "user_entity_view_filters", constraintName: "FKl1y77w88879q3g1di522fyby4", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "entity_view_filters", validate: "true")
    }

    changeSet(author: "bruno (generated)", id: "20220901134564565-01") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "group_id", tableName: "report_form")
    }

    changeSet(author: "bruno (generated)", id: "20220345345544245-01") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "group_id", tableName: "report_form_comments")
    }

    changeSet(author: "bruno (generated)", id: "20220923455325245-01") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "group_id", tableName: "report_form_files")
    }

    changeSet(author: "bruno (generated)", id: "2022023423424456-01") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "group_id", tableName: "report_form_recommendations")
    }

    changeSet(author: "bruno (generated)", id: "20223423423424445-01") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "group_id", tableName: "task_list")
    }

    changeSet(author: "bruno (generated)", id: "1647332552353645-6") {
        addColumn(tableName: "partner_setup") {
            column(name: "start_cycle", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "1647121252453463-43") {
        dropColumn(columnName: "reporting_calendar", tableName: "partner_setup")
    }

    changeSet(author: "bruno (generated)", id: "1647823423423123-1") {
        createTable(tableName: "calendar_trigger_dates") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "calendar_trigger_datesPK")
            }

            column(name: "partner_setup_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "period", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "start_date", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "end_date", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "started", type: "boolean") {
                constraints(nullable: "false")
            }

            column(name: "completed", type: "boolean") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "20220345453535635-01") {
        renameColumn(columnDataType: "varchar(255)", newColumnName: "process_instance_id", oldColumnName:"process_id", tableName: "report_form")
    }

    changeSet(author: "bruno (generated)", id: "202254345755353555-01") {
        renameColumn(columnDataType: "varchar(255)", newColumnName: "process_instance_id", oldColumnName:"process_id", tableName: "report_form_comments")
    }

    changeSet(author: "bruno (generated)", id: "2022067468764764745-01") {
        renameColumn(columnDataType: "varchar(255)", newColumnName: "process_instance_id", oldColumnName:"process_id", tableName: "report_form_files")
    }

    changeSet(author: "bruno (generated)", id: "202205635635476456-01") {
        renameColumn(columnDataType: "varchar(255)", newColumnName: "process_instance_id", oldColumnName:"process_id", tableName: "report_form_recommendations")
    }

    changeSet(author: "bruno (generated)", id: "1649072075746-8") {
        createTable(tableName: "user_partner") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "user_partnerPK")
            }

            column(name: "program_partner_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "user_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "1649072075746-10") {
        addUniqueConstraint(columnNames: "program_partner_id, user_id", constraintName: "UKcda91fa4eda58b1b8453181e9ec6", tableName: "user_partner")
    }

    changeSet(author: "omni-tech (generated)", id: "1648721492475-8") {
        addColumn(tableName: "kenga_group") {
            column(name: "parent_group_id", type: "varchar(255)")
        }
    }

    changeSet(author: "Marvin (generated)", id: "20220428102310-19987") {
        modifyDataType(columnName: "config_attribute", newDataType: "text", tableName: "request_map")
    }

    changeSet(author: "bruno (generated)", id: "1652364702164-9") {
        createTable(tableName: "grant_letter_of_interest") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "grant_letter_of_interestPK")
            }

            column(name: "letter_attachment", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "contact_person_number", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "organisation", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "address_contact_person", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "postal_address", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "physical_address", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "legal_status", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "email_address", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "city", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "contact_person", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "status", type: "VARCHAR(255)")

            column(name: "country", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "organization_type", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "email", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "website", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "program", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "acronym", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "1652364702164-10") {
        createTable(tableName: "grant_letter_of_interest_review") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "grant_letter_of_interest_reviewPK")
            }

            column(name: "definition_key", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "grant_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "has_been_reviewed", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "process_instance_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "due_diligence_report", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "due_diligence", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "comments", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "grant_letter_of_interest_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "decision", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "date_of_due_diligence", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "status", type: "VARCHAR(255)")
        }
    }

    changeSet(author: "bruno (generated)", id: "1652364702164-11") {
        createTable(tableName: "grant_planning_learning") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "grant_planning_learningPK")
            }

            column(name: "mel_framework_attachment", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "child_policy", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "email_contact_person", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "definition_key", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "structure", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "contact_person_number", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "grant_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "proposed_duration", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "contact_authorized_signatory", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "address_contact_person", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "completed_attachment", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "registration", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "financial_attachment", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "process_instance_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "strategic_plan", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "amount_requested", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "total_budget_amt", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "annual_work_plan", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "bank_details", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "proposed_start_date", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "other_organization", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "assessment_report", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "status", type: "VARCHAR(255)")

            column(name: "other_sources", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "name_authorized_signatory", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "list_members_attachment", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "1652364702164-12") {
        createTable(tableName: "grant_planning_learning_approve") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "grant_planning_learning_approvePK")
            }

            column(name: "comments", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "definition_key", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "grant_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "decision", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "grant_planning_learning_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "process_instance_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "status", type: "VARCHAR(255)")
        }
    }

    changeSet(author: "bruno (generated)", id: "1652364702164-13") {
        createTable(tableName: "grant_planning_learning_review") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "grant_planning_learning_reviewPK")
            }

            column(name: "comments", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "definition_key", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "grant_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "decision", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "grant_planning_learning_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "is_concept_inline", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "are_they_adhering", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "process_instance_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "status", type: "VARCHAR(255)")

            column(name: "does_it_adhere", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "1652364702164-14") {
        createTable(tableName: "grant_provide_learning_grant") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "grant_provide_learning_grantPK")
            }

            column(name: "comments", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "lead_agency", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "cluster_name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "definition_key", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "grant_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "process_instance_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "date_from", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "grant_amount", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "status", type: "VARCHAR(255)")

            column(name: "date_to", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "1652700022855-9") {
        createTable(tableName: "archive") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "archivePK")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "group_id", type: "VARCHAR(255)")

            column(name: "output_variables", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "input_variables", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "task_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "process_instance_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "task_definition_key", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "user_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "process_def_key", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "synced", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "form_id", type: "VARCHAR(255)")

            column(name: "task_name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "status", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "1652700022855-63") {
        dropColumn(columnName: "grant_letter_of_interest_id", tableName: "grant_letter_of_interest_review")
    }

    changeSet(author: "bruno (generated)", id: "1652700022855-64") {
        dropColumn(columnName: "grant_planning_learning_id", tableName: "grant_planning_learning_approve")
    }

    changeSet(author: "bruno (generated)", id: "1652700022855-65") {
        dropColumn(columnName: "grant_planning_learning_id", tableName: "grant_planning_learning_review")
    }

    changeSet(author: "bruno (generated)", id: "2022046456456656-01") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "mel_framework_attachment", tableName: "grant_planning_learning")
    }

    changeSet(author: "bruno (generated)", id: "202204565546-01") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "child_policy", tableName: "grant_planning_learning")
    }

    changeSet(author: "bruno (generated)", id: "202204345556-01") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "structure", tableName: "grant_planning_learning")
    }

    changeSet(author: "bruno (generated)", id: "202204242342356-01") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "completed_attachment", tableName: "grant_planning_learning")
    }

    changeSet(author: "bruno (generated)", id: "202204234235666456-01") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "strategic_plan", tableName: "grant_planning_learning")
    }

    changeSet(author: "bruno (generated)", id: "2022046456456456-01") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "annual_work_plan", tableName: "grant_planning_learning")
    }

    changeSet(author: "bruno (generated)", id: "202245645646456-01") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "assessment_report", tableName: "grant_planning_learning")
    }

    changeSet(author: "bruno (generated)", id: "2022456456454456-01") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "financial_attachment", tableName: "grant_planning_learning")
    }

    changeSet(author: "bruno (generated)", id: "2022045645645624456-01") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "registration", tableName: "grant_planning_learning")
    }

    changeSet(author: "bruno (generated)", id: "2022756765454456-01") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "list_members_attachment", tableName: "grant_planning_learning")
    }

    changeSet(author: "bruno (generated)", id: "1652790644052-9") {
        createTable(tableName: "grant_report") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "grant_reportPK")
            }

            column(name: "period_to", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "definition_key", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "period_from", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "grant_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "balance", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "grant_amount_utilised", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "amount_transferred", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "process_instance_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "report_attachment", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "grant_amount", type: "INT") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "1652795516897-9") {
        createTable(tableName: "grant_report_review") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "grant_report_reviewPK")
            }

            column(name: "comments", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "definition_key", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "grant_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "decision", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "achieve_intended_objectives", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "adhere_to_budget", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "process_instance_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "status", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "activities_inline_with_work_plan", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "1653384938689-62") {
        dropColumn(columnName: "acronym", tableName: "grant_letter_of_interest")
    }

    changeSet(author: "bruno (generated)", id: "1653384938689-63") {
        dropColumn(columnName: "address_contact_person", tableName: "grant_letter_of_interest")
    }

    changeSet(author: "bruno (generated)", id: "1653384938689-64") {
        dropColumn(columnName: "city", tableName: "grant_letter_of_interest")
    }

    changeSet(author: "bruno (generated)", id: "1653384938689-65") {
        dropColumn(columnName: "contact_person", tableName: "grant_letter_of_interest")
    }

    changeSet(author: "bruno (generated)", id: "1653384938689-66") {
        dropColumn(columnName: "contact_person_number", tableName: "grant_letter_of_interest")
    }

    changeSet(author: "bruno (generated)", id: "1653384938689-67") {
        dropColumn(columnName: "country", tableName: "grant_letter_of_interest")
    }

    changeSet(author: "bruno (generated)", id: "1653384938689-68") {
        dropColumn(columnName: "email", tableName: "grant_letter_of_interest")
    }

    changeSet(author: "bruno (generated)", id: "1653384938689-69") {
        dropColumn(columnName: "email_address", tableName: "grant_letter_of_interest")
    }

    changeSet(author: "bruno (generated)", id: "1653384938689-70") {
        dropColumn(columnName: "legal_status", tableName: "grant_letter_of_interest")
    }

    changeSet(author: "bruno (generated)", id: "1653384938689-71") {
        dropColumn(columnName: "letter_attachment", tableName: "grant_letter_of_interest")
    }

    changeSet(author: "bruno (generated)", id: "1653384938689-72") {
        dropColumn(columnName: "organization_type", tableName: "grant_letter_of_interest")
    }

    changeSet(author: "bruno (generated)", id: "1653384938689-73") {
        dropColumn(columnName: "physical_address", tableName: "grant_letter_of_interest")
    }

    changeSet(author: "bruno (generated)", id: "1653384938689-74") {
        dropColumn(columnName: "postal_address", tableName: "grant_letter_of_interest")
    }

    changeSet(author: "bruno (generated)", id: "1653384938689-75") {
        dropColumn(columnName: "website", tableName: "grant_letter_of_interest")
    }

    changeSet(author: "bruno (generated)", id: "1653384938689-9") {
        addColumn(tableName: "grant_letter_of_interest") {
            column(name: "documents", type: "LONGTEXT") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "1653384938689-10") {
        addColumn(tableName: "grant_letter_of_interest") {
            column(name: "financial", type: "LONGTEXT") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "1653384938689-11") {
        addColumn(tableName: "grant_letter_of_interest") {
            column(name: "ngos", type: "LONGTEXT") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "1653384938689-12") {
        addColumn(tableName: "grant_letter_of_interest") {
            column(name: "proposal", type: "LONGTEXT") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "20220901123312335-01") {
        modifyDataType(columnName: "organisation", newDataType: "LONGTEXT", tableName: "grant_letter_of_interest")
    }

    changeSet(author: "bruno (generated)", id: "1653401484689-59") {
        dropColumn(columnName: "annual_work_plan", tableName: "grant_planning_learning")
    }

    changeSet(author: "bruno (generated)", id: "1653401484689-60") {
        dropColumn(columnName: "assessment_report", tableName: "grant_planning_learning")
    }

    changeSet(author: "bruno (generated)", id: "1653401484689-61") {
        dropColumn(columnName: "child_policy", tableName: "grant_planning_learning")
    }

    changeSet(author: "bruno (generated)", id: "1653401484689-62") {
        dropColumn(columnName: "completed_attachment", tableName: "grant_planning_learning")
    }

    changeSet(author: "bruno (generated)", id: "1653401484689-63") {
        dropColumn(columnName: "financial_attachment", tableName: "grant_planning_learning")
    }

    changeSet(author: "bruno (generated)", id: "1653401484689-64") {
        dropColumn(columnName: "list_members_attachment", tableName: "grant_planning_learning")
    }

    changeSet(author: "bruno (generated)", id: "1653401484689-65") {
        dropColumn(columnName: "mel_framework_attachment", tableName: "grant_planning_learning")
    }

    changeSet(author: "bruno (generated)", id: "1653401484689-66") {
        dropColumn(columnName: "registration", tableName: "grant_planning_learning")
    }

    changeSet(author: "bruno (generated)", id: "1653401484689-67") {
        dropColumn(columnName: "strategic_plan", tableName: "grant_planning_learning")
    }

    changeSet(author: "bruno (generated)", id: "1653401484689-68") {
        dropColumn(columnName: "structure", tableName: "grant_planning_learning")
    }

    changeSet(author: "bruno (generated)", id: "1653638101429-81") {
        dropColumn(columnName: "address_contact_person", tableName: "grant_planning_learning")
    }

    changeSet(author: "bruno (generated)", id: "1653638101429-82") {
        dropColumn(columnName: "contact_person_number", tableName: "grant_planning_learning")
    }

    changeSet(author: "bruno (generated)", id: "1653638101429-83") {
        dropColumn(columnName: "email_contact_person", tableName: "grant_planning_learning")
    }

    changeSet(author: "bruno (generated)", id: "1653638101429-84") {
        dropColumn(columnName: "other_organization", tableName: "grant_planning_learning")
    }

    changeSet(author: "bruno (generated)", id: "1653638101429-10") {
        addColumn(tableName: "grant_planning_learning") {
            column(name: "city", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "1653638101429-11") {
        addColumn(tableName: "grant_planning_learning") {
            column(name: "country", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "1653638101429-12") {
        addColumn(tableName: "grant_planning_learning") {
            column(name: "title", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "1653638123155-12") {
        addColumn(tableName: "grant_planning_learning") {
            column(name: "attachment", type: "varchar(255)") {
                constraints(nullable: "true")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "1653638123452-12") {
        addColumn(tableName: "grant_planning_learning") {
            column(name: "six_months_managed", type: "LONGTEXT") {
                constraints(nullable: "true")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "165363812345664-12") {
        addColumn(tableName: "grant_planning_learning") {
            column(name: "activities_and_strategies", type: "LONGTEXT") {
                constraints(nullable: "true")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "16536381231244-12") {
        addColumn(tableName: "grant_planning_learning") {
            column(name: "risks_and_challenges", type: "LONGTEXT") {
                constraints(nullable: "true")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "165363812312344-12") {
        addColumn(tableName: "grant_planning_learning") {
            column(name: "learning_and_documentation", type: "LONGTEXT") {
                constraints(nullable: "true")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "165363123124453-12") {
        addColumn(tableName: "grant_planning_learning") {
            column(name: "cost_of_project", type: "varchar(255)") {
                constraints(nullable: "true")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "202209242526665-01") {
        modifyDataType(columnName: "input_variables", newDataType: "LONGTEXT", tableName: "archive")
    }

    changeSet(author: "bruno (generated)", id: "202202342352236545-01") {
        modifyDataType(columnName: "output_variables", newDataType: "LONGTEXT", tableName: "archive")
    }

    changeSet(author: "bruno (generated)", id: "20220234534534556-01") {
        modifyDataType(columnName: "comments", newDataType: "LONGTEXT", tableName: "grant_letter_of_interest_review")
    }

    changeSet(author: "bruno (generated)", id: "20220353453634634-01") {
        modifyDataType(columnName: "comments", newDataType: "LONGTEXT", tableName: "grant_planning_learning_review")
    }

    changeSet(author: "bruno (generated)", id: "1653662925340-1") {
        addNotNullConstraint(columnDataType: "LONGTEXT", columnName: "activities_and_strategies", tableName: "grant_planning_learning")
    }

    changeSet(author: "bruno (generated)", id: "1653662925340-2") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "are_they_adhering", tableName: "grant_planning_learning_review")
    }

    changeSet(author: "bruno (generated)", id: "1653662925340-3") {
        addNotNullConstraint(columnDataType: "varchar(255)", columnName: "attachment", tableName: "grant_planning_learning")
    }

    changeSet(author: "bruno (generated)", id: "1653662925340-4") {
        dropNotNullConstraint(columnDataType: "LONGTEXT", columnName: "comments", tableName: "grant_letter_of_interest_review")
    }

    changeSet(author: "bruno (generated)", id: "1653662925340-5") {
        dropNotNullConstraint(columnDataType: "LONGTEXT", columnName: "comments", tableName: "grant_planning_learning_review")
    }

    changeSet(author: "bruno (generated)", id: "1653662925340-7") {
        addNotNullConstraint(columnDataType: "varchar(255)", columnName: "cost_of_project", tableName: "grant_planning_learning", validate: "true")
    }

    changeSet(author: "bruno (generated)", id: "1653662925340-8") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "date_of_due_diligence", tableName: "grant_letter_of_interest_review")
    }

    changeSet(author: "bruno (generated)", id: "1653662925340-9") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "decision", tableName: "grant_letter_of_interest_review")
    }

    changeSet(author: "bruno (generated)", id: "1653662925340-10") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "decision", tableName: "grant_planning_learning_review")
    }

    changeSet(author: "bruno (generated)", id: "1653662925340-11") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "does_it_adhere", tableName: "grant_planning_learning_review")
    }

    changeSet(author: "bruno (generated)", id: "1653662925340-12") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "due_diligence", tableName: "grant_letter_of_interest_review")
    }

    changeSet(author: "bruno (generated)", id: "1653662925340-13") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "due_diligence_report", tableName: "grant_letter_of_interest_review")
    }

    changeSet(author: "bruno (generated)", id: "1653662925340-14") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "has_been_reviewed", tableName: "grant_letter_of_interest_review")
    }

    changeSet(author: "bruno (generated)", id: "1653662925340-17") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "is_concept_inline", tableName: "grant_planning_learning_review")
    }

    changeSet(author: "bruno (generated)", id: "1653662925340-18") {
        addNotNullConstraint(columnDataType: "LONGTEXT", columnName: "learning_and_documentation", tableName: "grant_planning_learning")
    }

    changeSet(author: "bruno (generated)", id: "1653662925340-20") {
        addNotNullConstraint(columnDataType: "LONGTEXT", columnName: "organisation", tableName: "grant_letter_of_interest")
    }

    changeSet(author: "bruno (generated)", id: "1653662925340-27") {
        addNotNullConstraint(columnDataType: "LONGTEXT", columnName: "risks_and_challenges", tableName: "grant_planning_learning")
    }

    changeSet(author: "bruno (generated)", id: "1653662925340-28") {
        addNotNullConstraint(columnDataType: "LONGTEXT", columnName: "six_months_managed", tableName: "grant_planning_learning")
    }

    changeSet(author: "bruno (generated)", id: "16536312423553466-12") {
        addColumn(tableName: "grant_letter_of_interest_review") {
            column(name: "user", type: "varchar(255)") {
                constraints(nullable: "true")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "16536234536463567-12") {
        addColumn(tableName: "grant_planning_learning_approve") {
            column(name: "user", type: "varchar(255)") {
                constraints(nullable: "true")
            }
        }
    }


    changeSet(author: "bruno (generated)", id: "16536312312445354-12") {
        addColumn(tableName: "grant_planning_learning_review") {
            column(name: "user", type: "varchar(255)") {
                constraints(nullable: "true")
            }
        }
    }


    changeSet(author: "bruno (generated)", id: "16536312234234253-12") {
        addColumn(tableName: "grant_provide_learning_grant") {
            column(name: "user", type: "varchar(255)") {
                constraints(nullable: "true")
            }
        }
    }


    changeSet(author: "bruno (generated)", id: "165323234235266678-12") {
        addColumn(tableName: "grant_report_review") {
            column(name: "user", type: "varchar(255)") {
                constraints(nullable: "true")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "1654169728070-84") {
        dropColumn(columnName: "acronym", tableName: "program_partner")
    }

    changeSet(author: "bruno (generated)", id: "1654169728070-85") {
        dropColumn(columnName: "email", tableName: "program_partner")
    }

    changeSet(author: "bruno (generated)", id: "1654169728070-86") {
        dropColumn(columnName: "lead_cluster", tableName: "program_partner")
    }

    changeSet(author: "bruno (generated)", id: "1654169728070-87") {
        dropColumn(columnName: "legal", tableName: "program_partner")
    }

    changeSet(author: "bruno (generated)", id: "1654169728070-88") {
        dropColumn(columnName: "name", tableName: "program_partner")
    }

    changeSet(author: "bruno (generated)", id: "1654169728070-89") {
        dropColumn(columnName: "postal_address", tableName: "program_partner")
    }

    changeSet(author: "bruno (generated)", id: "1654169728070-90") {
        dropColumn(columnName: "website", tableName: "program_partner")
    }

    changeSet(author: "bruno (generated)", id: "1654169728070-11") {
        addColumn(tableName: "program_partner") {
            column(name: "cluster", type: "varchar(255)")
        }
    }

    changeSet(author: "bruno (generated)", id: "1654169728070-12") {
        addColumn(tableName: "program_partner") {
            column(name: "email_contact_person", type: "varchar(255)")
        }
    }

    changeSet(author: "bruno (generated)", id: "1654169728070-13") {
        addColumn(tableName: "program_partner") {
            column(name: "organisation_type", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "1654169728070-15") {
        addColumn(tableName: "program_partner") {
            column(name: "telephone_contact_person", type: "varchar(255)")
        }
    }

    changeSet(author: "bruno (generated)", id: "1654681634891-11") {
        addColumn(tableName: "program_partner") {
            column(name: "data_collector", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "1654681634891-12") {
        addColumn(tableName: "program_partner") {
            column(name: "organisations_involved", type: "longtext") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "1652342523636-12") {
        addColumn(tableName: "program_category") {
            column(name: "form", type: "varchar(255)") {
                constraints(nullable: "true")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "1653346363278-28") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "program_id", tableName: "program_category")
    }

    changeSet(author: "bruno (generated)", id: "16523423456677-12") {
        addColumn(tableName: "project_milestone") {
            column(name: "form", type: "varchar(255)") {
                constraints(nullable: "true")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "16533462423566-28") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "program", tableName: "project_milestone")
    }

    changeSet(author: "bruno (generated)", id: "16533462342355-28") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "content", tableName: "report_form_recommendations")
    }

    changeSet(author: "bruno (generated)", id: "16533445639345-28") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "content", tableName: "report_form_comments")
    }

    changeSet(author: "omni-tech (generated)", id: "1655205123672-13") {
        addColumn(tableName: "tag") {
            column(name: "partner_id", type: "varchar(255)")
        }
    }

    changeSet(author: "bruno (generated)", id: "165412341535377-12") {
        addColumn(tableName: "program_partner") {
            column(name: "area_of_operation", type: "longtext") {
                constraints(nullable: "true")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "1655213433311-13") {
        createTable(tableName: "temp") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "tempPK")
            }

            column(name: "json_value", type: "LONGTEXT")

            column(name: "type", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "1655213433311-14") {
        addColumn(tableName: "grant_letter_of_interest_review") {
            column(name: "recommendations", type: "varchar(255)")
        }
    }

    changeSet(author: "bruno (generated)", id: "16533442342556-28") {
        dropNotNullConstraint(columnDataType: "longtext", columnName: "organisations_involved", tableName: "program_partner")
    }

    changeSet(author: "bruno (generated)", id: "1655986984290-14") {
        addColumn(tableName: "temp") {
            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "1655986984290-15") {
        addColumn(tableName: "temp") {
            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "1656057038764-14") {
        addColumn(tableName: "report_form") {
            column(name: "partner_id", type: "varchar(255)")
        }
    }

    changeSet(author: "bruno (generated)", id: "1656057038764-15") {
        addColumn(tableName: "report_form") {
            column(name: "partner_setup_id", type: "varchar(255)")
        }
    }

    changeSet(author: "bruno (generated)", id: "163894123235645-14") {
        addUniqueConstraint(columnNames: "task_id", constraintName: "UKcda91fa4eda523213r3t34581e9ec6", tableName: "task_list")
    }

    changeSet(author: "bruno (generated)", id: "16234524763546-28") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "data_collector", tableName: "program_partner")
    }

    changeSet(author: "bruno (generated)", id: "1656405168814-133") {
        dropColumn(columnName: "city", tableName: "grant_planning_learning")
    }

    changeSet(author: "bruno (generated)", id: "1656405168814-134") {
        dropColumn(columnName: "cost_of_project", tableName: "grant_planning_learning")
    }

    changeSet(author: "bruno (generated)", id: "1656405168814-135") {
        dropColumn(columnName: "country", tableName: "grant_planning_learning")
    }

    changeSet(author: "bruno (generated)", id: "1656405168814-14") {
        addColumn(tableName: "grant_planning_learning") {
            column(name: "cost_of_project_dollars", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "1656405168814-15") {
        addColumn(tableName: "grant_planning_learning") {
            column(name: "cost_of_project_local_currency", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "16562342627635-15") {
        addColumn(tableName: "grant_planning_learning") {
            column(name: "mou_attachment", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "16345353475345-134") {
        dropColumn(columnName: "lead_agency", tableName: "grant_provide_learning_grant")
    }

    changeSet(author: "bruno (generated)", id: "165234346535478-135") {
        dropColumn(columnName: "cluster_name", tableName: "grant_provide_learning_grant")
    }

    changeSet(author: "bruno (generated)", id: "1656418572486-132") {
        dropColumn(columnName: "amount_transferred", tableName: "grant_report")
    }

    changeSet(author: "bruno (generated)", id: "1656418572486-14") {
        addColumn(tableName: "grant_report") {
            column(name: "date_report_submitted", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "1656595297502-14") {
        createTable(tableName: "applicant") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "applicantPK")
            }

            column(name: "grant_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "username", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "organization", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "password", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "user_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "names", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "email", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "165334347898875-28") {
        dropNotNullConstraint(columnDataType: "longtext", columnName: "proposal", tableName: "grant_letter_of_interest")
    }

    changeSet(author: "bruno (generated)", id: "1653353447689566-28") {
        dropNotNullConstraint(columnDataType: "longtext", columnName: "financial", tableName: "grant_letter_of_interest")
    }

    changeSet(author: "omni-tech (generated)", id: "1656595864005-14") {
        createTable(tableName: "query_table") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "query_tablePK")
            }

            column(name: "kenga_group_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "query", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "permission", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "omni-tech (generated)", id: "1656657785112-14") {
        addColumn(tableName: "query_table") {
            column(name: "id_label", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "omni-tech (generated)", id: "1656687777121-14") {
        addColumn(tableName: "query_table") {
            column(name: "query_string", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }
    changeSet(author: "omni-tech (generated)", id: "1656690506020-132") {
        dropColumn(columnName: "query_string", tableName: "query_table")
    }

    changeSet(author: "omni-tech (generated)", id: "1656691348278-132") {
        dropColumn(columnName: "id_label", tableName: "query_table")
    }
    changeSet(author: "omni-tech (generated)", id: "20220701193135-01") {
        modifyDataType(columnName: "query", newDataType: "LONGTEXT", tableName: "query_table")
    }

    changeSet(author: "bruno (generated)", id: "1657118042234-14") {
        createTable(tableName: "long_term_grant_application") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "long_term_grant_applicationPK")
            }

            column(name: "structures_and_plans", type: "LONGTEXT") {
                constraints(nullable: "false")
            }

            column(name: "partnerships_and_networks", type: "LONGTEXT") {
                constraints(nullable: "false")
            }

            column(name: "definition_key", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "risks_and_challenges", type: "LONGTEXT") {
                constraints(nullable: "false")
            }

            column(name: "documents", type: "LONGTEXT") {
                constraints(nullable: "false")
            }

            column(name: "problem_addressed", type: "LONGTEXT") {
                constraints(nullable: "false")
            }

            column(name: "midterm_changes", type: "LONGTEXT") {
                constraints(nullable: "false")
            }

            column(name: "grant_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "total_project_cost_local_currency", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "change_envisioned", type: "LONGTEXT") {
                constraints(nullable: "false")
            }

            column(name: "contact_authorized_signatory", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "process_instance_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "amount_requested", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "reason_for_target_population", type: "LONGTEXT") {
                constraints(nullable: "false")
            }

            column(name: "bank_details", type: "LONGTEXT") {
                constraints(nullable: "false")
            }

            column(name: "target_population", type: "LONGTEXT") {
                constraints(nullable: "false")
            }

            column(name: "project_amount", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "activities", type: "LONGTEXT") {
                constraints(nullable: "false")
            }

            column(name: "project_title", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "project_duration", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "immediate_changes", type: "LONGTEXT") {
                constraints(nullable: "false")
            }

            column(name: "funding", type: "LONGTEXT") {
                constraints(nullable: "false")
            }

            column(name: "overall_goal", type: "LONGTEXT") {
                constraints(nullable: "false")
            }

            column(name: "problem_background", type: "LONGTEXT") {
                constraints(nullable: "false")
            }

            column(name: "status", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "name_authorized_signatory", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "total_project_cost_dollars", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "what_change_expected", type: "LONGTEXT") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "1657118042234-15") {
        createTable(tableName: "long_term_grant_review") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "long_term_grant_reviewPK")
            }

            column(name: "comments", type: "LONGTEXT") {
                constraints(nullable: "false")
            }

            column(name: "date_of_agreement", type: "VARCHAR(255)")

            column(name: "definition_key", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "grant_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "decision", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "user", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "type", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "are_they_adhering", type: "VARCHAR(255)")

            column(name: "process_instance_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "status", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "does_it_adhere", type: "VARCHAR(255)")

            column(name: "is_concept_in_line", type: "VARCHAR(255)")

            column(name: "application_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }
    changeSet(author: "omni-tech (generated)", id: "1656687235436561-14") {
        addColumn(tableName: "long_term_grant_application") {
            column(name: "project_proposed", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }
    changeSet(author: "bruno (generated)", id: "1657200535467-14") {
        addColumn(tableName: "long_term_grant_application") {
            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "1657200535467-15") {
        addColumn(tableName: "long_term_grant_review") {
            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "1657200535467-17") {
        addColumn(tableName: "long_term_grant_application") {
            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "1657200535467-18") {
        addColumn(tableName: "long_term_grant_review") {
            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "1657280025069-14") {
        createTable(tableName: "report_form_financial") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "report_form_financialPK")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "budget_line", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "quarter_expenses", type: "VARCHAR(255)")

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "variance", type: "VARCHAR(255)")

            column(name: "total_advanced", type: "VARCHAR(255)")

            column(name: "approved_budget", type: "VARCHAR(255)")

            column(name: "expense_to_date", type: "VARCHAR(255)")

            column(name: "reason_for_variance", type: "VARCHAR(255)")

            column(name: "report_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "1657280025069-15") {
        createTable(tableName: "report_form_performance") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "report_form_performancePK")
            }

            column(name: "quarter_achievement", type: "VARCHAR(255)")

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "overall_target", type: "VARCHAR(255)")

            column(name: "quarter_target", type: "VARCHAR(255)")

            column(name: "comment_on_result", type: "VARCHAR(255)")

            column(name: "milestone_id", type: "VARCHAR(255)")

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "output_indicators", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "cumulative_achievement", type: "VARCHAR(255)")

            column(name: "report_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "percentage_achievement", type: "VARCHAR(255)")
        }
    }

    changeSet(author: "bruno (generated)", id: "1657621596932-47") {
        dropColumn(columnName: "group_id", tableName: "report_form_comments")
    }

    changeSet(author: "bruno (generated)", id: "1657621596932-48") {
        dropColumn(columnName: "group_id", tableName: "report_form_recommendations")
    }

    changeSet(author: "bruno (generated)", id: "1657621596932-2") {
        addNotNullConstraint(columnDataType: "varchar(255)", columnName: "content", tableName: "report_form_comments", validate: "true")
    }

    changeSet(author: "bruno (generated)", id: "1657621596932-3") {
        addNotNullConstraint(columnDataType: "varchar(255)", columnName: "content", tableName: "report_form_recommendations", validate: "true")
    }

    changeSet(author: "bruno (generated)", id: "1657633001254-14") {
        createTable(tableName: "partner_setup_budget") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "partner_setup_budgetPK")
            }

            column(name: "partner_setup_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "approved_amount", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "budget_line", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "total_spent", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "milestone_id", type: "VARCHAR(255)")

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "1657633001254-15") {
        createTable(tableName: "partner_setup_disbursement_plan") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "partner_setup_disbursement_planPK")
            }

            column(name: "partner_setup_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "start_date", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "date_period", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "disbursement", type: "VARCHAR(255)")

            column(name: "end_date", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "bruno (generated)", id: "1657633001254-16") {
        createTable(tableName: "partner_setup_milestones") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "partner_setup_milestonesPK")
            }

            column(name: "partner_setup_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "overall_target", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "milestone_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "disaggregation", type: "LONGTEXT")
        }
    }

    changeSet(author: "bruno (generated)", id: "1657786567724-1") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "approved_amount", tableName: "partner_setup_budget")
    }

    changeSet(author: "bruno (generated)", id: "1657786567724-7") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "milestone_id", tableName: "partner_setup_milestones")
    }

    changeSet(author: "bruno (generated)", id: "1657786567724-17") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "total_spent", tableName: "partner_setup_budget")
    }

    changeSet(author: "bruno (generated)", id: "1657786567724-11") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "overall_target", tableName: "partner_setup_milestones")
    }

    changeSet(author: "bruno (generated)", id: "20220234534534665-01") {
        modifyDataType(columnName: "comment_on_result", newDataType: "LONGTEXT", tableName: "report_form_performance")
    }

    changeSet(author: "bruno (generated)", id: "2022034534534663-01") {
        modifyDataType(columnName: "reason_for_variance", newDataType: "LONGTEXT", tableName: "report_form_financial")
    }


}

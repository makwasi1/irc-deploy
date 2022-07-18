package com.kengamis

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class PartnerSetupMilestonesServiceSpec extends Specification {

    PartnerSetupMilestonesService partnerSetupMilestonesService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new PartnerSetupMilestones(...).save(flush: true, failOnError: true)
        //new PartnerSetupMilestones(...).save(flush: true, failOnError: true)
        //PartnerSetupMilestones partnerSetupMilestones = new PartnerSetupMilestones(...).save(flush: true, failOnError: true)
        //new PartnerSetupMilestones(...).save(flush: true, failOnError: true)
        //new PartnerSetupMilestones(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //partnerSetupMilestones.id
    }

    void "test get"() {
        setupData()

        expect:
        partnerSetupMilestonesService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<PartnerSetupMilestones> partnerSetupMilestonesList = partnerSetupMilestonesService.list(max: 2, offset: 2)

        then:
        partnerSetupMilestonesList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        partnerSetupMilestonesService.count() == 5
    }

    void "test delete"() {
        Long partnerSetupMilestonesId = setupData()

        expect:
        partnerSetupMilestonesService.count() == 5

        when:
        partnerSetupMilestonesService.delete(partnerSetupMilestonesId)
        sessionFactory.currentSession.flush()

        then:
        partnerSetupMilestonesService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        PartnerSetupMilestones partnerSetupMilestones = new PartnerSetupMilestones()
        partnerSetupMilestonesService.save(partnerSetupMilestones)

        then:
        partnerSetupMilestones.id != null
    }
}

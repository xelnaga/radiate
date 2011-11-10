package net.xelnaga.radiate.status

import hudson.model.Job
import hudson.model.Run
import spock.lang.Specification

class StatusFactorySpec extends Specification {

    private StatusFactory factory
    private Job mockJob

    void setup() {

        factory = new StatusFactory()
        mockJob = Mock(Job)
    }

    def 'make status when job has no last build'() {

        when:
            Status status = factory.makeStatus(mockJob)

        then:
            1 * mockJob.lastBuild >> null
            0 * _._

        and:
            status instanceof UnbuiltStatus

    }

    def 'make status when job has last build'() {

        when:
            Status status = factory.makeStatus(mockJob)

        then:
            1 * mockJob.lastBuild >> Mock(Run)
            0 * _._

        and:
            status instanceof StandardStatus
    }
}

package net.xelnaga.radiate.status

import spock.lang.Specification
import hudson.model.Job
import hudson.model.Cause
import hudson.model.Run

class StandardStatusSpec extends Specification {

    private Status status
    private Job mockJob
    private Run mockRun

    void setup() {

        mockJob = Mock(Job)
        mockRun = Mock(Run)
        status = new StandardStatus(mockJob)
    }

    def 'get name'() {

        when:
            String name = status.name

        then:
            1 * mockJob.name >> 'some-job'
            0 * _._

        and:
            name == 'some-job'
    }

    def 'get causes'() {

        given:
            List<Cause> dummy = []

        when:
            List<Cause> causes = status.causes

        then:
            1 * mockJob.lastBuild >> mockRun
            1 * mockRun.causes >> dummy
            0 * _._

        and:
            causes.is(dummy)
    }

    def 'get build number'() {

        when:
            int number = status.buildNumber

        then:
            1 * mockJob.lastBuild >> mockRun
            1 * mockRun.number >> 69
            0 * _._

        and:
            number == 69
    }

    def 'get duration ms'() {

        when:
            long duration = status.duration

        then:
            1 * mockJob.lastBuild >> mockRun
            1 * mockRun.duration >> 1234L
            0 * _._

        and:
            duration == 1234L
    }
}

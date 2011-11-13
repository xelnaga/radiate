package net.xelnaga.radiate.status

import hudson.model.Cause
import hudson.model.Job
import hudson.model.Result
import hudson.scm.ChangeLogSet
import spock.lang.Specification

class UnbuiltStatusTest extends Specification {

    private Status status
    private Job mockJob

    void setup() {

        mockJob = Mock(Job)
        status = new UnbuiltStatus(mockJob)
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

        when:
            List<Cause> causes = status.causes

        then:
            causes.empty
            0 * _._
    }
    def 'get build number'() {

        when:
            int number = status.buildNumber

        then:
            number == 0
            0 * _._
    }

    def 'get timestamp'() {

        when:
            String timestamp = status.timestamp

        then:
            timestamp == ""
            0 * _._
    }
    def 'get duration'() {

        when:
            long duration = status.duration

        then:
            duration == 0
            0 * _._
    }

    def 'get result'() {

        when:
            Result result = status.result

        then:
            result == Result.NOT_BUILT
            0 * _._
    }

    def 'get state'() {

        when:
            State state = status.state

        then:
            state == State.NotBuilt
            0 * _._
    }

    def 'get changes'() {

        when:
            Iterable<ChangeLogSet.Entry> changes = status.changes

        then:
            !changes.iterator().hasNext()
            0 * _._
    }
}

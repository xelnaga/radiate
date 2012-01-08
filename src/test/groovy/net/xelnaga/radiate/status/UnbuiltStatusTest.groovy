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
            long timestamp = status.timestamp

        then:
            timestamp == 0
            0 * _._
    }
    def 'get duration'() {

        when:
            long duration = status.duration

        then:
            duration == 0
            0 * _._
    }

    def 'get estimate'() {

        when:
            long estimate = status.estimate

        then:
            estimate == 0
            0 * _._
    }

    def 'get result'() {

        when:
            Result result = status.result

        then:
            0 * _._
        
        and:
            result == Result.NOT_BUILT
            
    }

    def 'get state'() {

        when:
            State state = status.state

        then:
            1 * mockJob.buildable >> true
            0 * _._
        
        and:
            state == State.NotBuilt         
    }
    
    def 'get state when disabled'() {
        
        when:
            State state = status.state
        
        then:
            1 * mockJob.buildable >> false
            0 * _._
        
        and:
            state == State.Disabled
    }

    def 'get changes'() {

        when:
            Iterable<ChangeLogSet.Entry> changes = status.changes

        then:
            0 * _._
        
        and:
            !changes.iterator().hasNext()
            
    }
}

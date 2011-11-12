package net.xelnaga.radiate.status

import spock.lang.Specification
import hudson.model.Job
import hudson.model.Cause
import hudson.model.Run
import hudson.model.Result
import hudson.scm.ChangeLogSet
import hudson.model.Build

class StandardStatusTest extends Specification {

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

    def 'get duration'() {

        when:
            long duration = status.duration

        then:
            1 * mockJob.lastBuild >> mockRun
            1 * mockRun.duration >> 1234L
            0 * _._

        and:
            duration == 1234L
    }

    def 'get result'() {

        when:
            Result actual = status.result

        then:
            1 * mockJob.lastBuild >> mockRun
            1 * mockRun.result >> Result.ABORTED
            0 * _._

        and:
            actual.is(Result.ABORTED)
    }

    def 'get changes'() {

        given:
            Build mockBuild = Mock(Build)
            Iterable<ChangeLogSet.Entry> changes = Mock(ChangeLogSet);

        when:
            Iterable<ChangeLogSet.Entry> actual = status.changes

        then:
            1 * mockJob.lastBuild >> mockBuild
            1 * mockBuild.changeSet >> changes

        and:
            actual.is(changes)
    }
}

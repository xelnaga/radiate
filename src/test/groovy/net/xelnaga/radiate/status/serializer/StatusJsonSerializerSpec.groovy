package net.xelnaga.radiate.status.serializer

import spock.lang.Specification
import net.xelnaga.radiate.status.Status
import net.xelnaga.radiate.status.State
import hudson.scm.ChangeLogSet
import hudson.model.Cause

class StatusJsonSerializerSpec extends Specification {

    private StatusJsonSerializer serializer

    private Status status

    void setup() {
        serializer = new StatusJsonSerializer()
    }

    def 'to json'() {

        given:
            Status mockStatus = Mock(Status)
            Cause mockCause = Mock(Cause)
            ChangeLogSet.Entry mockChange = Mock(ChangeLogSet.Entry)

        when:
            String json = serializer.toJson(mockStatus)

        then:
            1 * mockStatus.name >> 'somename'
            1 * mockStatus.state >> State.Failure
            1 * mockStatus.timestamp >> 12345678L
            1 * mockStatus.duration >> 1234L
            1 * mockStatus.causes >> [ mockCause ]
            1 * mockStatus.changes >> [ mockChange ]
            1 * mockCause.shortDescription >> "Effect"
            1 * mockChange.msg >> "Some message"
            0 * _._

            json == '{"name":"somename","state":"failure","timestamp":12345678,"duration":1234,"causes":["Effect"],"changes":["Some message"]}'
    }

    def 'to json for list'() {

        given:
            List<Status> mockStatuses = [ Mock(Status), Mock(Status) ]

        when:
            String json = serializer.toJson(mockStatuses)

        then:
            1 * mockStatuses[0].name >> 'somename'
            1 * mockStatuses[0].state >> State.Failure
            1 * mockStatuses[0].timestamp >> 12345678L
            1 * mockStatuses[0].duration >> 1234L
            1 * mockStatuses[0].causes >> []
            1 * mockStatuses[0].changes >> []

        and:
            1 * mockStatuses[1].name >> 'othername'
            1 * mockStatuses[1].state >> State.Success
            1 * mockStatuses[1].timestamp >> 87654321L
            1 * mockStatuses[1].duration >> 4321L
            1 * mockStatuses[1].causes >> []
            1 * mockStatuses[1].changes >> []

        and:
            0 * _._
            json == '[{"name":"somename","state":"failure","timestamp":12345678,"duration":1234,"causes":[],"changes":[]},{"name":"othername","state":"success","timestamp":87654321,"duration":4321,"causes":[],"changes":[]}]'
    }
}

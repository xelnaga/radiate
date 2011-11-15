package net.xelnaga.radiate.status.serializer

import spock.lang.Specification
import hudson.model.Cause

class CauseJsonSerializerSpec extends Specification {

    private CauseJsonSerializer serializer;

    void setup() {
        serializer = new CauseJsonSerializer();
    }

    def 'to json for cause'() {

        given:
            Cause mockCause = Mock(Cause)

        when:
            String json = serializer.toJson(mockCause)

        then:
            mockCause.msg >> "Hello \"World\""
            0 * _._
            json == "\"Hello \\\"World\\\"\""
    }

    def 'to json for list of causes'() {

        given:
            List<Cause> mockCauses = [ Mock(Cause), Mock(Cause) ]

        when:
            String json = serializer.toJson(mockCauses)

        then:
            mockCauses[0].msg >> "Hello \"World\""
            mockCauses[1].msg >> "Bubble Bobble"
            0 * _._
            json == "[\"Hello \\\"World\\\"\",\"Bubble Bobble\"]"
    }

    def 'to json for empty list of causes'() {

        given:
            List<Cause> causes = []

        when:
            String json = serializer.toJson(causes);

        then:
            json == "[]"
    }

}

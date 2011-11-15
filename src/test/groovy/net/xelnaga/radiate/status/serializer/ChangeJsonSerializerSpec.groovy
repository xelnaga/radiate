package net.xelnaga.radiate.status.serializer

import spock.lang.Specification
import hudson.scm.ChangeLogSet

class ChangeJsonSerializerSpec extends Specification {

    private ChangeJsonSerializer serializer;

    void setup() {
        serializer = new ChangeJsonSerializer();
    }

    def 'to json for change'() {

        given:
            ChangeLogSet.Entry mockEntry = Mock(ChangeLogSet.Entry)

        when:
            String json = serializer.toJson(mockEntry)

        then:
            mockEntry.msg >> "Hello \"World\""
            0 * _._
            json == "\"Hello \\\"World\\\"\""
    }

    def 'to json for list of changes'() {

        given:
            List<ChangeLogSet.Entry> mockChanges = [ Mock(ChangeLogSet.Entry), Mock(ChangeLogSet.Entry) ]

        when:
            String json = serializer.toJson(mockChanges)

        then:
            mockChanges[0].msg >> "Hello \"World\""
            mockChanges[1].msg >> "Bubble Bobble"
            0 * _._
            json == "[\"Hello \\\"World\\\"\",\"Bubble Bobble\"]"
    }

    def 'to json for empty list of changes'() {

        given:
            List<ChangeLogSet.Entry> changes = []

        when:
            String json = serializer.toJson(changes);

        then:
            json == "[]"
    }
}

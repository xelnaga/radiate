package net.xelnaga.radiate.status

import hudson.model.Job
import spock.lang.Specification

class UnbuiltStatusSpec extends Specification {

    private Status status

    void setup() {

        Job mockJob = Mock(Job)
        status = new UnbuiltStatus(mockJob)
    }
}

package net.xelnaga.radiate.status;

import hudson.model.Cause;
import hudson.model.Job;

import java.util.Collections;
import java.util.List;

class UnbuiltStatus extends Status {

    public UnbuiltStatus(Job job) {
        super(job);
    }

    @Override
    public List<Cause> getCauses() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public int getBuildNumber() {
        return 0;
    }

    @Override
    public long getDurationMs() {
        return 0;
    }
}

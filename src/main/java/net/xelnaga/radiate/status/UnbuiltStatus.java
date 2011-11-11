package net.xelnaga.radiate.status;

import hudson.model.Cause;
import hudson.model.Job;
import hudson.model.Result;

import java.util.ArrayList;
import java.util.List;

class UnbuiltStatus extends Status {

    public UnbuiltStatus(Job job) {
        super(job);
    }

    @Override
    public List<Cause> getCauses() {
        return new ArrayList<Cause>();
    }

    @Override
    public int getBuildNumber() {
        return 0;
    }

    @Override
    public long getDuration() {
        return 0;
    }

    @Override
    public Result getResult() {
        return Result.NOT_BUILT;
    }
}

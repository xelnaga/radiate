package net.xelnaga.radiate.status;

import hudson.model.Cause;
import hudson.model.Job;

import java.util.List;

public class StandardStatus extends Status {

    public StandardStatus(Job job) {
        super(job);
    }

    @Override
    public String getName() {
        return job.getName();
    }

    @Override
    public List<Cause> getCauses() {
        return job.getLastBuild().getCauses();
    }

    @Override
    public int getBuildNumber() {
        return job.getLastBuild().getNumber();
    }

    @Override
    public long getDuration() {
        return job.getLastBuild().getDuration();
    }
}

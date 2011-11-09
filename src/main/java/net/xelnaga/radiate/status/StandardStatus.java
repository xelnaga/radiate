package net.xelnaga.radiate.status;

import hudson.model.Cause;
import hudson.model.Job;

import java.util.List;

public class StandardStatus extends BaseJobStatus {

    public StandardStatus(Job job) {
        super(job);
    }

    public String getName() {
        return job.getName();
    }

    public List<Cause> getCauses() {
        return job.getLastBuild().getCauses();
    }

    public int getBuildNumber() {
        return job.getLastBuild().getNumber();
    }

    public long getDurationMs() {
        return job.getLastBuild().getDuration();
    }
}

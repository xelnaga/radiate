package net.xelnaga.radiate.status;

import hudson.model.Cause;
import hudson.model.Job;
import hudson.model.Result;
import hudson.scm.ChangeLogSet;

import java.util.List;

public abstract class Status {

    protected Job job;

    protected Status(Job job) {
        this.job = job;
    }

    public String getName() {
        return job.getName();
    }

    public abstract List<Cause> getCauses();
    public abstract int getBuildNumber();
    public abstract long getTimestamp();
    public abstract long getDuration();
    public abstract Result getResult();
    public abstract State getState();
    public abstract Iterable<ChangeLogSet.Entry> getChanges();
}

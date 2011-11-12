package net.xelnaga.radiate.status;

import hudson.model.*;
import hudson.scm.ChangeLogSet;
import hudson.util.RunList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Override
    public Result getResult() {
        return job.getLastBuild().getResult();
    }

    @Override
    public Iterable<ChangeLogSet.Entry> getChanges() {

        Run run = job.getLastBuild();
        if (run instanceof Build) {
            Build build = (Build) run;
            return build.getChangeSet();
        }

        return new ArrayList<ChangeLogSet.Entry>();
    }
}

package net.xelnaga.radiate.status;

import hudson.model.*;
import hudson.scm.ChangeLogSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StandardStatus extends Status {

    private static final Map<Result, State> STATE = new HashMap<Result, State>();

    static {
        STATE.put(Result.SUCCESS,   State.Success);
        STATE.put(Result.FAILURE,   State.Failure);
        STATE.put(Result.ABORTED,  State.Aborted);
        STATE.put(Result.NOT_BUILT, State.NotBuilt);
        STATE.put(Result.UNSTABLE,  State.Unstable);
    }

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
    public long getTimestamp() {
        return job.getLastBuild().getTimestamp().getTimeInMillis();
    }

    @Override
    public long getDuration() {

        Run build = job.getLastBuild();
        
        if (job.isBuilding()) {
            return System.currentTimeMillis() - build.getTimeInMillis();
        }

        return build.getDuration();
    }
    
    @Override
    public long getEstimate() {
        
        Run build = job.getLastBuild();

        return build.getEstimatedDuration();
    }

    @Override
    public Result getResult() {
        return job.getLastBuild().getResult();
    }

    @Override
    public State getState() {

        if (!job.isBuildable()) {
            return State.Disabled;
        }

        Run build = job.getLastBuild();

        if (build.isBuilding()) {
            return State.Building;
        }

        return findStateByMapping(build);
    }

    private State findStateByMapping(Run build) {

        Result result = build.getResult();

        if (STATE.containsKey(result)) {
            return STATE.get(result);
        }

        return State.Unknown;
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

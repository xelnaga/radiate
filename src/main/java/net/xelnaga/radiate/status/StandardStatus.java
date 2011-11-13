package net.xelnaga.radiate.status;

import hudson.model.*;
import hudson.scm.ChangeLogSet;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class StandardStatus extends Status {

    private static final Map<Result, State> STATE = new HashMap<Result, State>();

    static {
        STATE.put(Result.SUCCESS,   State.Success);
        STATE.put(Result.FAILURE,   State.Failure);
        STATE.put(Result.ABORTED,   State.Aborted);
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
    public String getTimestamp() {
        Calendar calendar = job.getLastBuild().getTimestamp();
        return new SimpleDateFormat("hh:mm a EEEE, dd MMMM").format(calendar.getTime());
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
    public State getState() {

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

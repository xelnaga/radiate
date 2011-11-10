package net.xelnaga.radiate.status;

import hudson.model.Job;

public class StatusFactory {

    public Status makeStatus(Job job) {

        if (job.getLastBuild() == null) {
            return new UnbuiltStatus(job);
        }

        return new StandardStatus(job);
    }
}

package net.xelnaga.radiate;

import hudson.Extension;
import hudson.model.Job;
import hudson.model.ListView;
import hudson.model.TopLevelItem;
import hudson.model.ViewDescriptor;
import org.kohsuke.stapler.DataBoundConstructor;

import java.util.ArrayList;
import java.util.List;

public class RadiateView extends ListView {

    @DataBoundConstructor
	public RadiateView(String name) {
		super(name);
	}

    @Extension
    public static final class RadiateViewDescriptor extends ViewDescriptor {

		@Override
		public String getDisplayName() {
			return "Radiate View";
		}
    }

    public List<JobState> getJobStates() {

        List<JobState> states = new ArrayList<JobState>();

        for (TopLevelItem item : getItems()) {
            if (item instanceof Job) {
                JobState state = new JobState((Job) item);
                states.add(state);
            }
        }

        return states;
    }
}

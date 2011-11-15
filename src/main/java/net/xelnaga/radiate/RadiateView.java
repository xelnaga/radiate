package net.xelnaga.radiate;

import hudson.Extension;
import hudson.model.Job;
import hudson.model.ListView;
import hudson.model.TopLevelItem;
import hudson.model.ViewDescriptor;
import net.xelnaga.radiate.status.Status;
import net.xelnaga.radiate.status.StatusFactory;
import net.xelnaga.radiate.status.StatusJsonSerializer;
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

    public List<Status> getStatuses() {

        List<Status> statuses = new ArrayList<Status>();
        StatusFactory factory = new StatusFactory();

        for (TopLevelItem item : getItems()) {
            if (item instanceof Job) {
                Status status = factory.makeStatus((Job) item);
                statuses.add(status);
            }
        }

        return statuses;
    }

    public String getJson() {

        StatusJsonSerializer serializer = new StatusJsonSerializer();
        return serializer.toJson(getStatuses());
    }
}

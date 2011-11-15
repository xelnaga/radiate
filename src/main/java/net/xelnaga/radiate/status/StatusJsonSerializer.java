package net.xelnaga.radiate.status;

import hudson.model.Cause;
import hudson.scm.ChangeLogSet;

import java.util.Iterator;
import java.util.List;

public class StatusJsonSerializer {

    public String toJson(List<Status> statuses) {

        String json = "[";

        Iterator<Status> iterator = statuses.iterator();

        while (iterator.hasNext()) {

            Status status = iterator.next();
            json += toJson(status);

            if (iterator.hasNext()) {
                json += ",";
            }
        }

        json += "]";

        return json;
    }

    public String toJson(Status status) {

        String json = "{";
        json += "\"name\":\"" + status.getName() + "\"";
        json += ",";
        json += "\"state\":\"" + status.getState().toString().toLowerCase() + "\"";
        json += ",";
        json += "\"timestamp\":" + status.getTimestamp();
        json += ",";
        json += "\"duration\":" + status.getDuration();
        json += ",";
        json += "\"causes\":" + toCauseJson(status.getCauses());
        json += ",";
        json += "\"changes\":" + toJson(status.getChanges());
        json += "}";

        return json;
    }

    public String toCauseJson(List<Cause> causes) {

        String json = "[";
        Iterator<Cause> iterator = causes.iterator();

        while (iterator.hasNext()) {

            Cause cause = iterator.next();
            json += toJson(cause);

            if (iterator.hasNext()) {
                json += ",";
            }
        }

        json += "]";

        return json;
    }

    public String toJson(Cause cause) {

        return "\"" + cause.getShortDescription() + "\"";
    }


    public String toJson(Iterable<ChangeLogSet.Entry> changes) {

        String json = "[";
        Iterator<ChangeLogSet.Entry> iterator = changes.iterator();

        while (iterator.hasNext()) {

            ChangeLogSet.Entry change = iterator.next();
            json += toJson(change);
            if (iterator.hasNext()) {
                json += ",";
            }
        }

        json += "]";

        return json;
    }

    public String toJson(ChangeLogSet.Entry change) {

        return "\"" + change.getMsgEscaped() + "\"";
    }
}
package net.xelnaga.radiate.status.serializer;

import net.xelnaga.radiate.status.Status;

import java.util.ArrayList;
import java.util.List;

public class StatusJsonSerializer extends BaseJsonSerializer {

    private List<String> _elements;
    private Status _status;

    public String toJson(List<Status> statuses) {

        List<String> elements = new ArrayList<String>();
        for (Status status : statuses) {
            String element = toJson(status);
            elements.add(element);
        }

        return makeArray(elements);
    }

    public String toJson(Status status) {

        _elements = new ArrayList<String>();
        _status = status;

        addName();
        addState();
        addTimestamp();
        addDuration();
        addEstimate();
        addCauses();
        addChanges();

        return makeMap(_elements);
    }

    public void addName() {

        String element = makeQuotedKey("name") + makeQuoted(_status.getName());
        _elements.add(element);
    }

    private void addState() {

        String element = makeQuotedKey("state") + makeQuoted(_status.getState().toString().toLowerCase());
        _elements.add(element);
    }

    private void addTimestamp() {

        String element = makeQuotedKey("timestamp") + _status.getTimestamp();
        _elements.add(element);
    }

    private void addDuration() {

        String element = makeQuotedKey("duration") + _status.getDuration();
        _elements.add(element);
    }

    private void addEstimate() {

        String element = makeQuotedKey("estimate") + _status.getEstimate();
        _elements.add(element);
    }

    private void addCauses() {

        CauseJsonSerializer causeJsonSerializer = new CauseJsonSerializer();
        String element = makeQuotedKey("causes") + causeJsonSerializer.toJson(_status.getCauses());
        _elements.add(element);
    }

    private void addChanges() {

        ChangeJsonSerializer changeJsonSerializer = new ChangeJsonSerializer();
        String element = makeQuotedKey("changes") + changeJsonSerializer.toJson(_status.getChanges());
        _elements.add(element);
    }
}
package net.xelnaga.radiate.status.serializer;

import hudson.model.Cause;

import java.util.ArrayList;
import java.util.List;

public class CauseJsonSerializer extends BaseJsonSerializer {

    public String toJson(List<Cause> causes) {

        List<String> elements = new ArrayList<String>();
        for (Cause cause : causes) {
            String element = toJson(cause);
            elements.add(element);
        }

        return makeArray(elements);
    }

    public String toJson(Cause cause) {

        String description = cause.getShortDescription();
        return makeQuotedAndEscaped(description);
    }
}

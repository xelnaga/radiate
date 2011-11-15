package net.xelnaga.radiate.status.serializer;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import java.util.List;

public abstract class BaseJsonSerializer {

    protected String makeArray(List<String> elements) {
        return "[" + StringUtils.join(elements.toArray(), ",") + "]";
    }

    protected String makeMap(List<String> elements) {
        return "{" + StringUtils.join(elements.toArray(), ",") + "}";
    }

    protected String makeQuoted(String s) {
        return '"' + s + '"';
    }

    protected String makeQuotedKey(String s) {
        return makeQuoted(s) + ":";
    }

    protected String makeQuotedAndEscaped(String s) {

        String escaped = StringEscapeUtils.escapeJavaScript(s);
        return makeQuoted(escaped);
    }
}

package org.jsoup.parser;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StreamParserTest_canReuse {
    private static final String BASE_URI = "";
    private static final String FIRST_DOCUMENT = "<p>One<p>Two";
    private static final String SECOND_DOCUMENT = "<div>Three<div>Four</div></div>";

    static void trackSeen(Element el, StringBuilder actual) {
        actual.append(el.tagName());
        if (el.hasAttr("id"))
            actual.append("#").append(el.id());
        if (!el.ownText().isEmpty())
            actual.append("[").append(el.ownText()).append("]");
        if (el.nextElementSibling() != null)
            actual.append("+");
        actual.append(";");
    }

    private static String streamSeenElements(StreamParser parser) {
        StringBuilder seen = new StringBuilder();
        parser.stream().forEach(el -> trackSeen(el, seen));
        return seen.toString();
    }

    @Test
    void canReuse() {
        StreamParser parser = new StreamParser(Parser.htmlParser());

        parser.parse(FIRST_DOCUMENT, BASE_URI);
        assertEquals("head+;p[One]+;p[Two];body;html;#root;", streamSeenElements(parser));

        parser.parse(SECOND_DOCUMENT, BASE_URI);
        assertEquals("head+;div[Four];div[Three];body;html;#root;", streamSeenElements(parser));

        assertEquals("", streamSeenElements(parser));
    }
}

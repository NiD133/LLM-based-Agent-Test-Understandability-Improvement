package org.jsoup.parser;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StreamParserTest_canIterateFragment {
    private static final String TABLE_FRAGMENT_WITH_IMPLIED_ROW_CLOSE =
        "<tr id=1><td>One</td><tr id=2><td>Two</td></tr><tr id=3><td>Three</td></tr>";

    private static final String EXPECTED_COMPLETION_ORDER =
        "td[One];tr#1+;td[Two];tr#2+;td[Three];tr#3;tbody;table;#root;";

    static void trackSeen(Element element, StringBuilder seenElements) {
        seenElements.append(element.tagName());
        if (element.hasAttr("id"))
            seenElements.append("#").append(element.id());
        if (!element.ownText().isEmpty())
            seenElements.append("[").append(element.ownText()).append("]");
        if (element.nextElementSibling() != null)
            seenElements.append("+");
        seenElements.append(";");
    }

    static boolean isClosed(StreamParser streamer) {
        return getReader(streamer) == null;
    }

    private static CharacterReader getReader(StreamParser streamer) {
        return streamer.document().parser().getTreeBuilder().reader;
    }

    @Test
    void canIterateFragment() {
        Element context = new Element("table");

        try (StreamParser parser = new StreamParser(Parser.htmlParser())
            .parseFragment(TABLE_FRAGMENT_WITH_IMPLIED_ROW_CLOSE, context, "")) {
            StringBuilder seenElements = new StringBuilder();
            Iterator<Element> iterator = parser.iterator();

            while (iterator.hasNext()) {
                trackSeen(iterator.next(), seenElements);
            }

            assertEquals(EXPECTED_COMPLETION_ORDER, seenElements.toString());
            assertTrue(isClosed(parser));
        }
    }
}

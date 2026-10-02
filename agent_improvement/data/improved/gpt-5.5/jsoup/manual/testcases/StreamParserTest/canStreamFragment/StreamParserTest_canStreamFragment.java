package org.jsoup.parser;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StreamParserTest_canStreamFragment {
    private static final String TABLE_FRAGMENT =
        "<tr id=1><td>One</td><tr id=2><td>Two</td></tr><tr id=3><td>Three</td></tr>";
    private static final String EXPECTED_STREAMED_ELEMENTS =
        "td[One];tr#1+;td[Two];tr#2+;td[Three];tr#3;tbody;table;#root;";

    @Test
    void canStreamFragment() {
        Element context = new Element("table");

        try (StreamParser parser = new StreamParser(Parser.htmlParser()).parseFragment(TABLE_FRAGMENT, context, "")) {
            StringBuilder streamedElements = new StringBuilder();

            parser.stream().forEachOrdered(element -> appendStreamedElement(element, streamedElements));

            assertEquals(EXPECTED_STREAMED_ELEMENTS, streamedElements.toString());
            assertTrue(isClosed(parser));
        }
    }

    private static void appendStreamedElement(Element element, StringBuilder streamedElements) {
        streamedElements.append(element.tagName());

        if (element.hasAttr("id")) {
            streamedElements.append("#").append(element.id());
        }

        if (!element.ownText().isEmpty()) {
            streamedElements.append("[").append(element.ownText()).append("]");
        }

        if (element.nextElementSibling() != null) {
            streamedElements.append("+");
        }

        streamedElements.append(";");
    }

    private static boolean isClosed(StreamParser parser) {
        return getReader(parser) == null;
    }

    private static CharacterReader getReader(StreamParser parser) {
        return parser.document().parser().getTreeBuilder().reader;
    }
}

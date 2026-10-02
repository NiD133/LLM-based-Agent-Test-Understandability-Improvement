package org.jsoup.parser;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StreamParserTest_canStreamFragmentXml {
    private static final String FragmentHtml =
        "<tr id=1><td>One</td></tr><tr id=2><td>Two</td></tr><tr id=3><td>Three</td></tr>";
    private static final String ExpectedStreamOrder =
        "td[One];tr#1+;td[Two];tr#2+;td[Three];tr#3;#root;";

    @Test
    void canStreamFragmentXml() throws IOException {
        Element context = new Element("Other");

        try (StreamParser parser = new StreamParser(Parser.xmlParser()).parseFragment(FragmentHtml, context, "")) {
            StringBuilder streamedElements = new StringBuilder();
            parser.stream().forEachOrdered(element -> appendStreamedElement(element, streamedElements));

            assertEquals(ExpectedStreamOrder, streamedElements.toString());
            assertTrue(isClosed(parser));

            List<Node> completedFragment = parser.completeFragment();
            assertEquals(3, completedFragment.size());
            assertEquals("tr", completedFragment.get(0).nodeName());
        }
    }

    private static void appendStreamedElement(Element element, StringBuilder streamedElements) {
        streamedElements.append(element.tagName());
        if (element.hasAttr("id"))
            streamedElements.append("#").append(element.id());
        if (!element.ownText().isEmpty())
            streamedElements.append("[").append(element.ownText()).append("]");
        if (element.nextElementSibling() != null)
            streamedElements.append("+");
        streamedElements.append(";");
    }

    private static boolean isClosed(StreamParser parser) {
        return getReader(parser) == null;
    }

    private static CharacterReader getReader(StreamParser parser) {
        return parser.document().parser().getTreeBuilder().reader;
    }
}

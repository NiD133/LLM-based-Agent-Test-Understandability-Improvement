package org.jsoup.parser;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StreamParserTest_canStreamFragmentXml {

    /**
     * Appends a short descriptor for {@code el} to {@code actual}:
     * {@code tagName[#id][ownText][+];} where {@code +} means a next sibling
     * existed at the time the element was emitted by the stream.
     */
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

    static boolean isClosed(StreamParser streamer) {
        return getReader(streamer) == null;
    }

    private static CharacterReader getReader(StreamParser streamer) {
        return streamer.document().parser().getTreeBuilder().reader;
    }

    @Test
    void canStreamFragmentXml() throws IOException {
        // Three table rows parsed as an XML fragment (no wrapping document)
        String xmlFragment = "<tr id=1><td>One</td></tr>"
            + "<tr id=2><td>Two</td></tr>"
            + "<tr id=3><td>Three</td></tr>";
        Element context = new Element("Other");

        // Expected emission order: children before parents, '+' marks elements
        // that had a next sibling at the moment they were emitted by the stream.
        // '#root' is the synthetic root node emitted at the end of a fragment parse.
        String expectedEmissions = "td[One];tr#1+;td[Two];tr#2+;td[Three];tr#3;#root;";

        try (StreamParser parser = new StreamParser(Parser.xmlParser()).parseFragment(xmlFragment, context, "")) {
            StringBuilder emissionLog = new StringBuilder();
            parser.stream().forEachOrdered(el -> trackSeen(el, emissionLog));

            assertEquals(expectedEmissions, emissionLog.toString());

            // The parser should close itself once the input is fully consumed
            assertTrue(isClosed(parser));

            // completeFragment() returns the top-level nodes of the fragment
            List<Node> fragmentNodes = parser.completeFragment();
            assertEquals(3, fragmentNodes.size());
            assertEquals("tr", fragmentNodes.get(0).nodeName());
        }
    }
}

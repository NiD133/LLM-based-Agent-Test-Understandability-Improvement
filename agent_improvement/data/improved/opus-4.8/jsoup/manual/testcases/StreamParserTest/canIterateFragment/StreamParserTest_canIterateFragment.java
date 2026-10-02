package org.jsoup.parser;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StreamParserTest_canIterateFragment {

    /**
     * Appends a compact, human-readable signature of the given element to {@code summary}, so that the
     * order and shape of streamed elements can be asserted with a single string comparison. The format is:
     * <pre>tagName[#id][[ownText]][+];</pre>
     * where {@code #id} appears only when the element has an id, {@code [ownText]} only when it has own text,
     * and a trailing {@code +} marks that the element already had a next sibling when it was emitted.
     */
    static void describeElement(Element el, StringBuilder summary) {
        summary.append(el.tagName());
        if (el.hasAttr("id"))
            summary.append("#").append(el.id());
        if (!el.ownText().isEmpty())
            summary.append("[").append(el.ownText()).append("]");
        if (el.nextElementSibling() != null)
            summary.append("+");
        summary.append(";");
    }

    /**
     * A StreamParser is considered closed once its underlying reader has been released. We reach into the
     * tree builder to read that reader directly, since there is no public flag exposing the closed state.
     */
    static boolean isClosed(StreamParser parser) {
        CharacterReader reader = parser.document().parser().getTreeBuilder().reader;
        return reader == null;
    }

    @Test
    void canIterateFragment() {
        // Fragment parsing exposes the same progressive parse as a full-document stream, just via parseFragment().
        // The input intentionally omits the first </tr>; the following <tr> infers that closing tag.
        String html = "<tr id=1><td>One</td><tr id=2><td>Two</td></tr><tr id=3><td>Three</td></tr>";
        Element context = new Element("table");

        try (StreamParser parser = new StreamParser(Parser.htmlParser()).parseFragment(html, context, "")) {
            StringBuilder emittedOrder = new StringBuilder();
            Iterator<Element> it = parser.iterator();
            while (it.hasNext()) {
                describeElement(it.next(), emittedOrder);
            }

            // Elements are emitted in document order as each one closes, so children precede their parents.
            // A trailing '+' means the element already had a next sibling when it was emitted.
            // Only the fragment is returned (plus the context element at the end of the stack), not a full document.
            assertEquals("td[One];tr#1+;td[Two];tr#2+;td[Three];tr#3;tbody;table;#root;", emittedOrder.toString());

            // Once the input is read to completion the parser closes itself and releases its reader.
            assertTrue(isClosed(parser));
        }
    }
}

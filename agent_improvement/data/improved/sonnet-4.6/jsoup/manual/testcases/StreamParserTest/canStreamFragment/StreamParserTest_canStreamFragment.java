package org.jsoup.parser;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StreamParserTest_canStreamFragment {

    /**
     * Appends a compact representation of {@code el} to {@code actual}:
     * - tagName (e.g. "td")
     * - "#id" if the element has an id attribute
     * - "[text]" if the element has direct text content
     * - "+" if the element had a next sibling at the time of emission
     * - always ends with ";"
     *
     * Example: an emitted <tr id="1"> with a following sibling appends "tr#1+;"
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

    /** Returns true when the StreamParser has closed its underlying reader (i.e. parsing is complete). */
    static boolean isClosed(StreamParser streamer) {
        return getReader(streamer) == null;
    }

    private static CharacterReader getReader(StreamParser streamer) {
        return streamer.document().parser().getTreeBuilder().reader;
    }

    /**
     * Verifies that a fragment parse streams elements in document (bottom-up) order:
     * children are emitted before their parents, and siblings are emitted in source order.
     *
     * The fragment "<tr id=1>...<tr id=2>...<tr id=3>..." parsed inside a <table> context
     * should yield:
     *   td[One]; tr#1+; td[Two]; tr#2+; td[Three]; tr#3; tbody; table; #root;
     *
     * The "+" suffix means the element had a next sibling at emission time.
     * Note: no full document is produced — only the fragment's elements and the context element stack.
     *
     * Also asserts that the parser is closed once the stream is fully consumed.
     */
    @Test
    void canStreamFragment() {
        String fragmentHtml = "<tr id=1><td>One</td><tr id=2><td>Two</td></tr><tr id=3><td>Three</td></tr>";
        Element tableContext = new Element("table");

        try (StreamParser parser = new StreamParser(Parser.htmlParser()).parseFragment(fragmentHtml, tableContext, "")) {
            StringBuilder seen = new StringBuilder();
            parser.stream().forEachOrdered(el -> trackSeen(el, seen));

            // Elements are emitted bottom-up (children before parents), siblings in source order.
            // "+" means the element had a next sibling when it was emitted.
            String expectedEmissionOrder =
                "td[One];tr#1+;"      // first row's cell, then first row (has sibling tr#2)
                + "td[Two];tr#2+;"    // second row's cell, then second row (has sibling tr#3)
                + "td[Three];tr#3;"   // third row's cell, then third row (no more siblings)
                + "tbody;table;#root;"; // enclosing context elements flushed at end

            assertEquals(expectedEmissionOrder, seen.toString());
            assertTrue(isClosed(parser), "Parser should be closed after stream is fully consumed");
        }
    }
}

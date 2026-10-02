package org.jsoup.parser;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests that StreamParser.iterator() correctly emits elements in document order
 * when parsing an HTML fragment, and closes the reader once iteration completes.
 */
public class StreamParserTest_canIterateFragment {

    /**
     * Appends a short descriptor for {@code el} to {@code actual}:
     * <ul>
     *   <li>tag name (e.g. "td")</li>
     *   <li>"#id" if the element has an id attribute</li>
     *   <li>"[text]" if the element has direct text</li>
     *   <li>"+" if the element had a next sibling at emission time</li>
     *   <li>";" as a delimiter between entries</li>
     * </ul>
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

    /**
     * Returns true when the StreamParser has closed its underlying CharacterReader,
     * which happens automatically once all input has been consumed.
     */
    static boolean isClosed(StreamParser streamer) {
        return getReader(streamer) == null;
    }

    private static CharacterReader getReader(StreamParser streamer) {
        return streamer.document().parser().getTreeBuilder().reader;
    }

    @Test
    void canIterateFragment() {
        // Fragment HTML with a missing </tr> — the parser infers it when the next <tr> begins.
        String html = "<tr id=1><td>One</td><tr id=2><td>Two</td></tr><tr id=3><td>Three</td></tr>";
        // Parse within a <table> context so the browser-like fragment rules apply.
        Element context = new Element("table");

        try (StreamParser parser = new StreamParser(Parser.htmlParser()).parseFragment(html, context, "")) {
            StringBuilder seen = new StringBuilder();

            // Consume every element emitted by the iterator and record its descriptor.
            Iterator<Element> it = parser.iterator();
            while (it.hasNext()) {
                trackSeen(it.next(), seen);
            }

            // Expected emission order (children before parents; "+" means a next sibling existed at emission time).
            // Note: we receive the fragment elements plus the context element at the top of the stack,
            // not a full document.
            assertEquals("td[One];tr#1+;td[Two];tr#2+;td[Three];tr#3;tbody;table;#root;", seen.toString());

            // Exhausting the iterator must close the underlying reader automatically.
            assertTrue(isClosed(parser));
        }
    }
}

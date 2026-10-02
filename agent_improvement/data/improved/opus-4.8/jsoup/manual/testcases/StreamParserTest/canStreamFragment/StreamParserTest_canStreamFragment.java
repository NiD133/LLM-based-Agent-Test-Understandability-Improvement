package org.jsoup.parser;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Verifies that {@link StreamParser} can parse an HTML fragment and emit its elements,
 * in document order, via {@link StreamParser#stream()}.
 */
public class StreamParserTest_canStreamFragment {

    /**
     * Appends a compact, human-readable signature of {@code element} to {@code summary}, capturing
     * the details this test asserts on:
     * <ul>
     *     <li>the tag name,</li>
     *     <li>{@code #id} when the element has an {@code id} attribute,</li>
     *     <li>{@code [text]} when the element has its own text,</li>
     *     <li>a trailing {@code +} when the element had a next sibling at the moment it was emitted.</li>
     * </ul>
     * Each signature is terminated with a {@code ;}.
     */
    static void appendElementSignature(Element element, StringBuilder summary) {
        summary.append(element.tagName());
        if (element.hasAttr("id"))
            summary.append("#").append(element.id());
        if (!element.ownText().isEmpty())
            summary.append("[").append(element.ownText()).append("]");
        if (element.nextElementSibling() != null)
            summary.append("+");
        summary.append(";");
    }

    /**
     * Returns true once the parser has read its input to completion and released the underlying reader.
     * Reaches through the parser internals as a back door, since there is no public "is closed" API.
     */
    static boolean isClosed(StreamParser parser) {
        CharacterReader reader = parser.document().parser().getTreeBuilder().reader;
        return reader == null;
    }

    @Test
    void canStreamFragment() {
        String html = "<tr id=1><td>One</td><tr id=2><td>Two</td></tr><tr id=3><td>Three</td></tr>";
        Element tableContext = new Element("table");

        try (StreamParser parser = new StreamParser(Parser.htmlParser()).parseFragment(html, tableContext, "")) {
            StringBuilder emittedElements = new StringBuilder();
            parser.stream().forEachOrdered(element -> appendElementSignature(element, emittedElements));

            // Elements are emitted in document order; children come before their parents. The trailing
            // '+' marks elements that already had a next sibling when emitted. Note we only get the
            // fragment plus the context element at the end of the stack, not a full document.
            assertEquals(
                "td[One];tr#1+;td[Two];tr#2+;td[Three];tr#3;tbody;table;#root;",
                emittedElements.toString());

            // Streaming to completion closes the parser and releases the reader.
            assertTrue(isClosed(parser));
        }
    }
}

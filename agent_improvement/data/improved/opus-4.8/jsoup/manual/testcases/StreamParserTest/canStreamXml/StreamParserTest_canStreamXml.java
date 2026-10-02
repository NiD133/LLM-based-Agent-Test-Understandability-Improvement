package org.jsoup.parser;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies that {@link StreamParser} progressively emits XML elements in document order
 * when streaming, and that each element is complete (children included) with its next
 * (empty) sibling present at the time it is emitted.
 */
public class StreamParserTest_canStreamXml {

    /**
     * Appends a compact, human-readable signature of {@code el} to {@code trace}, capturing
     * the facts this test cares about for each emitted element:
     * <ul>
     *     <li>the tag name,</li>
     *     <li>{@code #id} when the element has an {@code id} attribute,</li>
     *     <li>{@code [text]} when the element has its own (non-child) text,</li>
     *     <li>a trailing {@code +} when the element already has a next sibling at emission time.</li>
     * </ul>
     * Each signature is terminated with {@code ;}.
     */
    private static void appendEmittedElement(Element el, StringBuilder trace) {
        trace.append(el.tagName());
        if (el.hasAttr("id"))
            trace.append("#").append(el.id());
        if (!el.ownText().isEmpty())
            trace.append("[").append(el.ownText()).append("]");
        if (el.nextElementSibling() != null)
            trace.append("+");
        trace.append(";");
    }

    @Test
    void canStreamXml() {
        String xml = "<outmost><DIV id=1>D1</DIV><div id=2>D2<p id=3><span>P One</p><p id=4>P Two</p></div><div id=5>D3<p id=6>P three</p>";

        try (StreamParser parser = new StreamParser(Parser.xmlParser()).parse(xml, "")) {
            // Record every element as it is streamed, in emission order.
            StringBuilder emissionTrace = new StringBuilder();
            parser.stream().forEachOrdered(el -> appendEmittedElement(el, emissionTrace));

            // Elements are emitted in document order as each is closed, so children precede their
            // parents. A trailing "+" marks elements that already had a next sibling when emitted.
            String expectedEmissionOrder =
                "DIV#1[D1]+;span[P One];p#3+;p#4[P Two];div#2[D2]+;p#6[P three];div#5[D3];outmost;#root;";
            assertEquals(expectedEmissionOrder, emissionTrace.toString());
        }
    }
}

package org.jsoup.parser;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StreamParserTest_canIterate {

    /**
     * Appends a compact, human-readable summary of {@code element} to {@code summary}, encoding the
     * details this test cares about for each emitted element:
     * <ul>
     *     <li>the tag name (e.g. {@code div})</li>
     *     <li>{@code #id} if the element has an {@code id} attribute</li>
     *     <li>{@code [text]} if the element has its own (non-child) text</li>
     *     <li>a trailing {@code +} if the element had a next sibling when it was emitted</li>
     * </ul>
     * Each element's summary is terminated with {@code ;}.
     */
    static void appendSummary(Element element, StringBuilder summary) {
        summary.append(element.tagName());
        if (element.hasAttr("id"))
            summary.append("#").append(element.id());
        if (!element.ownText().isEmpty())
            summary.append("[").append(element.ownText()).append("]");
        if (element.nextElementSibling() != null)
            summary.append("+");
        summary.append(";");
    }

    @Test
    void canIterate() {
        // Iterating a StreamParser emits each Element as it is completed (children before parents).
        // This mirrors the stream() behaviour, just exposed through the Iterator interface.
        String html = "<title>Test</title></head><div id=1>D1</div><div id=2>D2<p id=3><span>P One</p><p id=4>P Two</p></div><div id=5>D3<p id=6>P three</p>";
        StreamParser parser = new StreamParser(Parser.htmlParser()).parse(html, "");

        StringBuilder summary = new StringBuilder();
        Iterator<Element> elements = parser.iterator();
        while (elements.hasNext()) {
            appendSummary(elements.next(), summary);
        }

        // Verifies the document-order emission. The "+" markers confirm that an element already had a
        // next sibling at the moment it was emitted (see appendSummary for the encoding).
        String expected = "title[Test];head+;div#1[D1]+;span[P One];p#3+;p#4[P Two];div#2[D2]+;p#6[P three];div#5[D3];body;html;#root;";
        assertEquals(expected, summary.toString());
    }
}

package org.jsoup.parser;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StreamParserTest_canStream {

    /**
     * Appends a compact, human-readable signature of {@code el} to {@code log}, so that the order and
     * shape of the streamed elements can be asserted as a single string. The signature is:
     * <pre>tagName[#id][[ownText]][+];</pre>
     * where the optional {@code #id} and {@code [ownText]} parts are only added when present, and the
     * trailing {@code +} marks that the element already had a next sibling when it was emitted.
     */
    static void describeElement(Element el, StringBuilder log) {
        log.append(el.tagName());
        if (el.hasAttr("id"))
            log.append("#").append(el.id());
        if (!el.ownText().isEmpty())
            log.append("[").append(el.ownText()).append("]");
        if (el.nextElementSibling() != null)
            log.append("+");
        log.append(";");
    }

    @Test
    void canStream() {
        String html = "<title>Test</title></head><div id=1>D1</div><div id=2>D2<p id=3><span>P One</p><p id=4>P Two</p></div><div id=5>D3<p id=6>P three</p>";

        try (StreamParser parser = new StreamParser(Parser.htmlParser()).parse(html, "")) {
            StringBuilder streamedElements = new StringBuilder();
            parser.stream().forEachOrdered(el -> describeElement(el, streamedElements));

            // Elements are emitted in document order as each one is closed, so children appear before
            // their parents. A trailing "+" indicates the element already had a next sibling when emitted.
            String expectedOrder =
                "title[Test];head+;div#1[D1]+;span[P One];p#3+;p#4[P Two];div#2[D2]+;p#6[P three];div#5[D3];body;html;#root;";
            assertEquals(expectedOrder, streamedElements.toString());
        }
    }
}

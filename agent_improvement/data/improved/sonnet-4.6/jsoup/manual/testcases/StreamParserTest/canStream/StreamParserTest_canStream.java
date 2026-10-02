package org.jsoup.parser;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StreamParserTest_canStream {

    /**
     * Appends a short token describing {@code el} to {@code actual}.
     * Token format: tagName[#id][text][+];
     *   - #id    present when the element carries an id attribute
     *   - [text] present when the element has non-empty own text
     *   - +      present when the element had a next sibling at emission time
     *   - ;      always appended as a delimiter between tokens
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

    @Test
    void canStream() {
        // HTML with a title, nested divs, paragraphs, and a span — exercises the
        // streaming emission order (children before parents, siblings in document order).
        String html = "<title>Test</title></head>"
            + "<div id=1>D1</div>"
            + "<div id=2>D2<p id=3><span>P One</p><p id=4>P Two</p></div>"
            + "<div id=5>D3<p id=6>P three</p>";

        StringBuilder seen = new StringBuilder();
        try (StreamParser parser = new StreamParser(Parser.htmlParser()).parse(html, "")) {
            parser.stream().forEachOrdered(el -> trackSeen(el, seen));
        }

        // Emission is depth-first: each element appears after all its descendants.
        // The "+" marker confirms the element had a next sibling when it was emitted.
        String expected =
            "title[Test];head+;"
            + "div#1[D1]+;"
            + "span[P One];p#3+;p#4[P Two];div#2[D2]+;"
            + "p#6[P three];div#5[D3];"
            + "body;html;#root;";
        assertEquals(expected, seen.toString());
    }
}

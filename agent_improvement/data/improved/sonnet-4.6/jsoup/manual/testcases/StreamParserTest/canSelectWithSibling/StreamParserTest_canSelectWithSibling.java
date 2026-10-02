package org.jsoup.parser;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class StreamParserTest_canSelectWithSibling {

    /**
     * Builds a StreamParser positioned at the start of a two-div document.
     * The second parse() call resets and re-initialises the parser on the same HTML,
     * ensuring a clean stream position regardless of construction-time side-effects.
     */
    static StreamParser basic() {
        String html = "<div>One</div><div><p>Two</div>";
        StreamParser parser = new StreamParser(Parser.htmlParser()).parse(html, "");
        parser.parse(html, "");
        return parser;
    }

    @Test
    void canSelectWithSibling() throws IOException {
        StreamParser parser = basic();

        // Advance the stream to the first <div> that satisfies :first-of-type.
        Element firstDiv = parser.expectNext("div:first-of-type");
        assertEquals("One", firstDiv.text());

        // After the stream has moved past the first <div>, the remaining element
        // is the second <div>.  It is NOT :first-of-type because a preceding div
        // sibling is now known, so selectNext must return null.
        Element secondMatch = parser.selectNext("div:first-of-type");
        assertNull(secondMatch);
    }
}

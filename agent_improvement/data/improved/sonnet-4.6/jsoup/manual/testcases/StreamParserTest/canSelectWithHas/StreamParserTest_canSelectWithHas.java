package org.jsoup.parser;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class StreamParserTest_canSelectWithHas {

    /**
     * Creates a StreamParser initialized with two sequential parse calls on the same HTML.
     * The second parse() call resets and re-initializes the parser, so the effective input
     * is "<div>One</div><div><p>Two</div>" starting from a clean state.
     */
    static StreamParser basic() {
        String html = "<div>One</div><div><p>Two</div>";
        StreamParser parser = new StreamParser(Parser.htmlParser()).parse(html, "");
        parser.parse(html, "");
        return parser;
    }

    /**
     * Verifies that StreamParser.expectNext() correctly handles the CSS ":has()" pseudo-class,
     * which selects an element that contains a given descendant. Here, "div:has(p)" should
     * match the second div (which wraps a <p> tag), and its text content should be "Two".
     */
    @Test
    void canSelectWithHas() throws IOException {
        StreamParser parser = basic();

        // Advance the stream parser to the first div that contains a <p> descendant
        Element divWithParagraph = parser.expectNext("div:has(p)");

        assertEquals("Two", divWithParagraph.text());
    }
}

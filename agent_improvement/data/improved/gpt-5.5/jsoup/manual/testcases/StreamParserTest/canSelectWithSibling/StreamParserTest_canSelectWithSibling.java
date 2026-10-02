package org.jsoup.parser;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class StreamParserTest_canSelectWithSibling {
    private static final String HTML_WITH_TWO_DIV_SIBLINGS = "<div>One</div><div><p>Two</div>";
    private static final String FIRST_DIV_SELECTOR = "div:first-of-type";

    private static StreamParser parserForSiblingSelection() {
        StreamParser parser = new StreamParser(Parser.htmlParser()).parse(HTML_WITH_TWO_DIV_SIBLINGS, "");
        parser.parse(HTML_WITH_TWO_DIV_SIBLINGS, "");
        return parser;
    }

    @Test
    void canSelectWithSibling() throws IOException {
        StreamParser parser = parserForSiblingSelection();

        Element firstDiv = parser.expectNext(FIRST_DIV_SELECTOR);
        assertEquals("One", firstDiv.text());

        Element nextFirstDiv = parser.selectNext(FIRST_DIV_SELECTOR);
        assertNull(nextFirstDiv);
    }
}

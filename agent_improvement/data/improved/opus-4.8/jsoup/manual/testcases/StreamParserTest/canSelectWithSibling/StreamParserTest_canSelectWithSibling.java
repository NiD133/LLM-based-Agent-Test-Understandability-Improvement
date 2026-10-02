package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import org.jsoup.nodes.Element;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class StreamParserTest_canSelectWithSibling {

    /** HTML with two sibling {@code <div>} elements; only the first carries the text "One". */
    private static final String HTML_WITH_SIBLINGS = "<div>One</div><div><p>Two</div>";

    /** Creates a StreamParser primed with {@link #HTML_WITH_SIBLINGS}. */
    private static StreamParser newParserForSiblings() {
        StreamParser parser = new StreamParser(Parser.htmlParser()).parse(HTML_WITH_SIBLINGS, "");
        parser.parse(HTML_WITH_SIBLINGS, "");
        return parser;
    }

    @Test
    void canSelectWithSibling() throws IOException {
        StreamParser parser = newParserForSiblings();

        // The first matching <div> is found and its text is "One".
        Element firstDiv = parser.expectNext("div:first-of-type");
        assertEquals("One", firstDiv.text());

        // A second selectNext for the same query has nothing left to match.
        Element noFurtherMatch = parser.selectNext("div:first-of-type");
        assertNull(noFurtherMatch);
    }
}

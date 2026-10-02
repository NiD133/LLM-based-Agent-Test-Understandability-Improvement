package org.jsoup.parser;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies that {@link StreamParser#expectNext(String)} accepts a CSS selector that uses the
 * {@code :has()} pseudo-class, returning the next element that matches while streaming the input.
 */
public class StreamParserTest_canSelectWithHas {

    /**
     * Builds a StreamParser primed with a small HTML document. The {@code <div>} containing a
     * {@code <p>} is the element we expect {@code div:has(p)} to select.
     */
    private static StreamParser newParserWithSampleHtml() {
        String html = "<div>One</div><div><p>Two</div>";
        StreamParser parser = new StreamParser(Parser.htmlParser()).parse(html, "");
        // Re-feed the same input to confirm a StreamParser can be reused with a fresh parse.
        parser.parse(html, "");
        return parser;
    }

    @Test
    void canSelectWithHas() throws IOException {
        StreamParser parser = newParserWithSampleHtml();

        Element divWithParagraph = parser.expectNext("div:has(p)");

        assertEquals("Two", divWithParagraph.text());
    }
}

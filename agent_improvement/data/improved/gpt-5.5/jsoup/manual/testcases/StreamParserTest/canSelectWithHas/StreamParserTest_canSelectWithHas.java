package org.jsoup.parser;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StreamParserTest_canSelectWithHas {
    private static final String HTML_WITH_NESTED_PARAGRAPH = "<div>One</div><div><p>Two</div>";
    private static final String BASE_URI = "";
    private static final String DIV_CONTAINING_PARAGRAPH_SELECTOR = "div:has(p)";

    private static StreamParser parserForNestedParagraphDocument() {
        StreamParser parser = new StreamParser(Parser.htmlParser()).parse(HTML_WITH_NESTED_PARAGRAPH, BASE_URI);
        parser.parse(HTML_WITH_NESTED_PARAGRAPH, BASE_URI);
        return parser;
    }

    @Test
    void canSelectWithHas() throws IOException {
        StreamParser parser = parserForNestedParagraphDocument();

        Element matchingDiv = parser.expectNext(DIV_CONTAINING_PARAGRAPH_SELECTOR);

        assertEquals("Two", matchingDiv.text());
    }
}

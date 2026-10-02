package org.jsoup.parser;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StreamParserTest_doesNotReadPastParse {
    private static final String HTML_WITH_UNCONSUMED_CHILD = "<div>One</div><div><p>Two</div>";
    private static final String FIRST_DIV_SELECTOR = "div";
    private static final String UNCONSUMED_CHILD_START = "<p>Two";

    private static StreamParser newParserAtStartOfHtml() {
        StreamParser parser = new StreamParser(Parser.htmlParser()).parse(HTML_WITH_UNCONSUMED_CHILD, "");
        parser.parse(HTML_WITH_UNCONSUMED_CHILD, "");
        return parser;
    }

    private static CharacterReader getReader(StreamParser streamer) {
        return streamer.document().parser().getTreeBuilder().reader;
    }

    @Test
    void doesNotReadPastParse() throws IOException {
        StreamParser streamer = newParserAtStartOfHtml();

        Element firstDiv = streamer.expectFirst(FIRST_DIV_SELECTOR);
        Element secondDiv = firstDiv.nextElementSibling();

        assertNotNull(secondDiv);
        assertEquals("div", secondDiv.tagName());
        assertEquals(0, secondDiv.childNodeSize());
        assertTrue(getReader(streamer).matches(UNCONSUMED_CHILD_START));
    }
}

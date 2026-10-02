package org.jsoup.parser;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StreamParserTest_doesNotReadPastParse {

    /** HTML used by the test: a closed div, then a second div whose child {@code <p>} is not yet reached. */
    private static final String HTML = "<div>One</div><div><p>Two</div>";

    /** Creates a StreamParser primed with {@link #HTML}, ready to be consumed. */
    private static StreamParser basic() {
        StreamParser parser = new StreamParser(Parser.htmlParser()).parse(HTML, "");
        parser.parse(HTML, "");
        return parser;
    }

    /** Exposes the parser's underlying reader so the test can inspect how far the input has been consumed. */
    private static CharacterReader getReader(StreamParser streamer) {
        return streamer.document().parser().getTreeBuilder().reader;
    }

    @Test
    void doesNotReadPastParse() throws IOException {
        StreamParser streamer = basic();

        // Consuming the first div forces the parser to read just far enough to confirm it has a next sibling.
        Element firstDiv = streamer.expectFirst("div");

        // The second div has been read as a sibling, but its child <p> has not been parsed yet.
        Element secondDiv = firstDiv.nextElementSibling();
        assertNotNull(secondDiv);
        assertEquals("div", secondDiv.tagName());
        assertEquals(0, secondDiv.childNodeSize());

        // Confirm the reader is parked exactly at the unconsumed "<p>Two".
        assertTrue(getReader(streamer).matches("<p>Two"));
    }
}

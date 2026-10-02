package org.jsoup.parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests that a StreamParser automatically closes its underlying reader
 * once its element stream has been fully consumed.
 */
public class StreamParserTest_closedOnStreamDrained {

    /**
     * Creates a StreamParser pre-loaded with a simple two-div HTML fragment.
     * parse() is called a second time to verify that re-parsing resets state
     * while keeping the same input.
     */
    static StreamParser basic() {
        String html = "<div>One</div><div><p>Two</div>";
        StreamParser parser = new StreamParser(Parser.htmlParser()).parse(html, "");
        parser.parse(html, "");
        return parser;
    }

    /**
     * Returns true when the StreamParser has been closed (its internal reader is null).
     * Inspecting the CharacterReader is a back-door into parser state used only in tests.
     */
    static boolean isClosed(StreamParser streamer) {
        return getReader(streamer) == null;
    }

    private static CharacterReader getReader(StreamParser streamer) {
        return streamer.document().parser().getTreeBuilder().reader;
    }

    @Test
    void closedOnStreamDrained() {
        // Set up a fresh parser; the underlying reader should still be open
        StreamParser parser = basic();
        assertFalse(isClosed(parser), "Parser should be open before the stream is consumed");

        // Fully drain the stream — the parser should close itself when no more elements remain
        long elementCount = parser.stream().count();
        assertEquals(7, elementCount, "Two-div HTML fragment should produce exactly 7 elements");

        assertTrue(isClosed(parser), "Parser should close its reader after the stream is fully drained");
    }
}

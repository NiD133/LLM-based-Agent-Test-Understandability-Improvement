package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StreamParserTest_closedOnStreamDrained {

    /**
     * Checks if the StreamParser has been closed by inspecting whether its internal
     * CharacterReader has been set to null (which happens after the input is fully consumed).
     * This uses package-private field access as a back-door into the parser's internal state.
     */
    static boolean isClosed(StreamParser streamer) {
        return streamer.document().parser().getTreeBuilder().reader == null;
    }

    /**
     * Creates a StreamParser initialized with a simple two-div HTML snippet.
     * Note: parse() is called twice intentionally — the second call re-initializes the parser
     * with the same input, overwriting the first (tests that re-parse works correctly).
     */
    static StreamParser createBasicStreamParser() {
        String html = "<div>One</div><div><p>Two</div>";
        StreamParser parser = new StreamParser(Parser.htmlParser()).parse(html, "");
        parser.parse(html, "");
        return parser;
    }

    @Test
    void closedOnStreamDrained() {
        StreamParser streamer = createBasicStreamParser();

        // Stream is open before any elements have been consumed
        assertFalse(isClosed(streamer));

        // Consuming the full stream yields 7 elements: #document, html, head, body,
        // div (One), div, and p (Two) — the StreamParser auto-closes when the stream is exhausted
        long count = streamer.stream().count();
        assertEquals(7, count);

        // After the stream is fully drained, the parser must have auto-closed its reader
        assertTrue(isClosed(streamer));
    }
}

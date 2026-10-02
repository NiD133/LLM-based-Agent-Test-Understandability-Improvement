package org.jsoup.parser;

import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests that a StreamParser automatically closes its underlying reader
 * once the element stream has been fully consumed.
 */
public class StreamParserTest_closedOnStreamDrained {

    /**
     * Creates a StreamParser initialized with a simple two-div HTML snippet.
     * The second parse() call re-initializes the parser on the same input,
     * which is the intended setup for this test.
     */
    static StreamParser basic() {
        String html = "<div>One</div><div><p>Two</div>";
        StreamParser parser = new StreamParser(Parser.htmlParser()).parse(html, "");
        parser.parse(html, "");
        return parser;
    }

    /**
     * Returns true if the StreamParser has been closed (i.e., its internal
     * CharacterReader has been released). A null reader indicates closure.
     */
    static boolean isClosed(StreamParser streamer) {
        return getReader(streamer) == null;
    }

    /**
     * Accesses the CharacterReader held by the parser's TreeBuilder.
     * Returns null after the parser has been closed.
     */
    private static CharacterReader getReader(StreamParser streamer) {
        return streamer.document().parser().getTreeBuilder().reader;
    }

    /**
     * Verifies that consuming all elements from the stream causes the
     * StreamParser to automatically close its underlying reader.
     *
     * The HTML "<div>One</div><div><p>Two</div>" yields 7 elements in
     * document order (child elements are emitted before their parents):
     * p, div#2, html, head, body, div#1, and the document root — or
     * equivalently whatever the streaming order produces for this input.
     */
    @Test
    void closedOnStreamDrained() {
        StreamParser streamer = basic();

        assertFalse(isClosed(streamer), "StreamParser should be open before any elements are consumed");

        long elementCount = streamer.stream().count();
        assertEquals(7, elementCount, "Streaming the full document should yield exactly 7 elements");

        assertTrue(isClosed(streamer), "StreamParser should be automatically closed after the stream is fully drained");
    }
}

package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Verifies that a {@link StreamParser} closes its underlying input (the reader) automatically
 * once the element {@link StreamParser#stream() stream} has been fully drained.
 */
public class StreamParserTest_closedOnStreamDrained {

    private static final String HTML = "<div>One</div><div><p>Two</div>";

    /** The number of elements the sample HTML above yields when streamed to completion. */
    private static final long EXPECTED_ELEMENT_COUNT = 7;

    /** Creates a StreamParser primed with the sample HTML, ready to be streamed. */
    private static StreamParser newStreamParser() {
        return new StreamParser(Parser.htmlParser())
            .parse(HTML, "")
            .parse(HTML, "");
    }

    /**
     * Reports whether the parser has closed its input. Reaching into the tree builder's reader is a
     * back door, but it is the only way to observe that the resource was released: a null reader
     * means the input has been closed.
     */
    private static boolean isClosed(StreamParser streamer) {
        CharacterReader reader = streamer.document().parser().getTreeBuilder().reader;
        return reader == null;
    }

    @Test
    void closedOnStreamDrained() {
        StreamParser streamer = newStreamParser();

        // The input has not been consumed yet, so the reader is still open.
        assertFalse(isClosed(streamer));

        // Draining the whole stream reads all the input and emits every element.
        long emittedElements = streamer.stream().count();
        assertEquals(EXPECTED_ELEMENT_COUNT, emittedElements);

        // Having read the input to the end, the parser has released (closed) its reader.
        assertTrue(isClosed(streamer));
    }
}

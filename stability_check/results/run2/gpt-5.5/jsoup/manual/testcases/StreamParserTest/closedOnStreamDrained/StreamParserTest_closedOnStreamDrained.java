package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StreamParserTest_closedOnStreamDrained {
    private static final String HTML = "<div>One</div><div><p>Two</div>";
    private static final String BASE_URI = "";
    private static final long EXPECTED_EMITTED_ELEMENTS = 7;

    @Test
    void closedOnStreamDrained() {
        StreamParser streamer = parserWithInputReadyToStream();

        assertFalse(isClosed(streamer));
        long emittedElements = streamer.stream().count();

        assertEquals(EXPECTED_EMITTED_ELEMENTS, emittedElements);
        assertTrue(isClosed(streamer));
    }

    private static StreamParser parserWithInputReadyToStream() {
        StreamParser parser = new StreamParser(Parser.htmlParser()).parse(HTML, BASE_URI);
        parser.parse(HTML, BASE_URI);
        return parser;
    }

    private static boolean isClosed(StreamParser streamer) {
        return getReader(streamer) == null;
    }

    private static CharacterReader getReader(StreamParser streamer) {
        return streamer.document().parser().getTreeBuilder().reader;
    }
}

package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StreamParserTest_closedOnStreamDrained {
    private static final String HTML_WITH_NESTED_ELEMENTS = "<div>One</div><div><p>Two</div>";
    private static final String BASE_URI = "";
    private static final int EXPECTED_STREAMED_ELEMENT_COUNT = 7;

    @Test
    void closedOnStreamDrained() {
        StreamParser streamer = parserReadyToStream();

        assertFalse(isReaderReleased(streamer));

        long streamedElementCount = streamer.stream().count();

        assertEquals(EXPECTED_STREAMED_ELEMENT_COUNT, streamedElementCount);
        assertTrue(isReaderReleased(streamer));
    }

    private static StreamParser parserReadyToStream() {
        StreamParser parser = new StreamParser(Parser.htmlParser()).parse(HTML_WITH_NESTED_ELEMENTS, BASE_URI);
        parser.parse(HTML_WITH_NESTED_ELEMENTS, BASE_URI);
        return parser;
    }

    private static boolean isReaderReleased(StreamParser streamer) {
        return currentReader(streamer) == null;
    }

    private static CharacterReader currentReader(StreamParser streamer) {
        return streamer.document().parser().getTreeBuilder().reader;
    }
}

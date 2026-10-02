package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StreamParserTest_closedOnStreamDrained {

    private static final String HTML = "<div>One</div><div><p>Two</div>";
    private static final int EXPECTED_ELEMENT_COUNT = 7;

    /** Creates a StreamParser primed to parse {@link #HTML}, before any elements have been consumed. */
    private static StreamParser newStreamParserFor(String html) {
        StreamParser parser = new StreamParser(Parser.htmlParser()).parse(html, "");
        parser.parse(html, ""); // re-initialise the parse to exercise parser reuse
        return parser;
    }

    /**
     * Reports whether the parser has released its underlying reader. Once the input is fully read, the
     * StreamParser closes and discards the reader, so a null reader means the parser is closed.
     */
    private static boolean isClosed(StreamParser parser) {
        CharacterReader reader = parser.document().parser().getTreeBuilder().reader;
        return reader == null;
    }

    @Test
    void closesReaderOnceStreamIsFullyDrained() {
        StreamParser parser = newStreamParserFor(HTML);

        // Before consuming anything, the parser still holds an open reader.
        assertFalse(isClosed(parser), "parser should be open before the stream is consumed");

        // Draining the stream parses the entire input, emitting every element.
        long emittedElements = parser.stream().count();
        assertEquals(EXPECTED_ELEMENT_COUNT, emittedElements);

        // Having read the whole input, the parser has closed and released its reader.
        assertTrue(isClosed(parser), "parser should be closed after the stream is fully drained");
    }
}

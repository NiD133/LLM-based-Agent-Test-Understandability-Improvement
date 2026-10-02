package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Verifies that a {@link StreamParser} automatically closes its underlying input
 * once the element stream has been fully drained.
 */
public class StreamParserTest_closedOnStreamDrained {

    /** HTML used for the parse; it contains exactly 7 elements (html, head, body, 2 divs, p). */
    private static final String HTML = "<div>One</div><div><p>Two</div>";

    private static final int EXPECTED_ELEMENT_COUNT = 7;

    /** Builds a StreamParser primed to parse {@link #HTML}, but not yet consumed. */
    private static StreamParser newPrimedParser() {
        StreamParser parser = new StreamParser(Parser.htmlParser()).parse(HTML, "");
        parser.parse(HTML, "");
        return parser;
    }

    /**
     * Reports whether the parser's input has been closed. The StreamParser exposes no public
     * "is closed" method, so we reach in through the document's tree builder: once the reader
     * is released it becomes null.
     */
    private static boolean isClosed(StreamParser parser) {
        CharacterReader reader = parser.document().parser().getTreeBuilder().reader;
        return reader == null;
    }

    @Test
    void closedOnStreamDrained() {
        StreamParser parser = newPrimedParser();

        // The reader is still open before anything is consumed.
        assertFalse(isClosed(parser));

        // Draining the whole stream emits every element and exhausts the input.
        long elementCount = parser.stream().count();
        assertEquals(EXPECTED_ELEMENT_COUNT, elementCount);

        // Reaching the end of the input closes the reader automatically.
        assertTrue(isClosed(parser));
    }
}

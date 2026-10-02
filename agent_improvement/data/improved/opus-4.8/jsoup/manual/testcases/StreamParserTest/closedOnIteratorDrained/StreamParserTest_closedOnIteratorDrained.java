package org.jsoup.parser;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Verifies that once a {@link StreamParser}'s iterator has been fully drained, the parser has read
 * all of its input and has released its backing reader (i.e. it is closed).
 */
public class StreamParserTest_closedOnIteratorDrained {

    /** The HTML used for the parse; it yields 7 elements once fully parsed. */
    private static final String HTML = "<div>One</div><div><p>Two</div>";

    /**
     * Builds a StreamParser primed with {@link #HTML}. The input is parsed twice to mirror the
     * original fixture: the second {@code parse} call resets the parser onto fresh input before it
     * is consumed.
     */
    private static StreamParser newPrimedParser() {
        StreamParser parser = new StreamParser(Parser.htmlParser()).parse(HTML, "");
        parser.parse(HTML, "");
        return parser;
    }

    /**
     * Reports whether the parser has closed its underlying reader. A drained parser nulls out the
     * reader, so a {@code null} reader signals a closed parser.
     */
    private static boolean isClosed(StreamParser parser) {
        CharacterReader reader = parser.document().parser().getTreeBuilder().reader;
        return reader == null;
    }

    @Test
    void closedOnIteratorDrained() {
        StreamParser parser = newPrimedParser();

        int elementCount = 0;
        Iterator<Element> elements = parser.iterator();
        while (elements.hasNext()) {
            elements.next();
            elementCount++;
        }

        assertEquals(7, elementCount);
        assertTrue(isClosed(parser), "parser should be closed after the iterator is drained");
    }
}

package org.jsoup.parser;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StreamParserTest_closedOnIteratorDrained {
    private static final int EXPECTED_EMITTED_ELEMENT_COUNT = 7;

    private static StreamParser parserForBasicDocument() {
        String html = "<div>One</div><div><p>Two</div>";
        StreamParser parser = new StreamParser(Parser.htmlParser()).parse(html, "");
        parser.parse(html, "");
        return parser;
    }

    private static boolean parserReaderClosed(StreamParser parser) {
        return currentReader(parser) == null;
    }

    private static CharacterReader currentReader(StreamParser parser) {
        return parser.document().parser().getTreeBuilder().reader;
    }

    @Test
    void closedOnIteratorDrained() {
        StreamParser parser = parserForBasicDocument();
        Iterator<Element> iterator = parser.iterator();

        int emittedElementCount = 0;
        while (iterator.hasNext()) {
            iterator.next();
            emittedElementCount++;
        }

        assertEquals(EXPECTED_EMITTED_ELEMENT_COUNT, emittedElementCount);
        assertTrue(parserReaderClosed(parser));
    }
}

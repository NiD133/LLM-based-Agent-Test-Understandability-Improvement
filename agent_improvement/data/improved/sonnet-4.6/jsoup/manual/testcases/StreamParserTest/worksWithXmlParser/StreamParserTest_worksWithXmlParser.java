package org.jsoup.parser;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class StreamParserTest_worksWithXmlParser {

    /**
     * Returns true if the StreamParser's underlying reader has been closed.
     * The reader is closed automatically once the input is fully consumed.
     * Accessing it via the tree builder is a back-door, but it lets us assert
     * that streaming drives the parse all the way to the end.
     */
    private static boolean isClosed(StreamParser streamer) {
        return getReader(streamer) == null;
    }

    private static CharacterReader getReader(StreamParser streamer) {
        return streamer.document().parser().getTreeBuilder().reader;
    }

    @Test
    void worksWithXmlParser() throws IOException {
        // Three <p> siblings, each containing exactly 3 characters of text ("One", "Two", "Thr").
        String xmlInput = "<div><p>One</p><p>Two</p><p>Thr</p></div>";
        StreamParser streamer = new StreamParser(Parser.xmlParser()).parse(xmlInput, "");

        int matchCount = 0;
        Element pElement;
        while ((pElement = streamer.selectNext("p")) != null) {
            // Each <p> text must be exactly 3 characters long.
            assertEquals(3, pElement.text().length());
            // Remove matched elements mid-iteration to verify that streaming supports
            // DOM mutation during a live parse without breaking traversal.
            pElement.remove();
            matchCount++;
        }

        // All three <p> elements should have been visited.
        assertEquals(3, matchCount);
        // Removing during iteration must have cleared every <p> from the live document.
        assertEquals(0, streamer.document().select("p").size());
        // selectNext() should have driven the parse to completion, closing the reader.
        assertTrue(isClosed(streamer));
    }
}

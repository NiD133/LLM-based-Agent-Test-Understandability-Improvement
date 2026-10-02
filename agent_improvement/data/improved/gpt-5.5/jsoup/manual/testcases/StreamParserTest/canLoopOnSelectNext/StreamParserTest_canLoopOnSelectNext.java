package org.jsoup.parser;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StreamParserTest_canLoopOnSelectNext {
    private static final String THREE_PARAGRAPHS = "<div><p>One<p>Two<p>Thr</div>";
    private static final String PARAGRAPH_SELECTOR = "p";

    private static boolean isClosed(StreamParser streamer) {
        return getReader(streamer) == null;
    }

    private static CharacterReader getReader(StreamParser streamer) {
        return streamer.document().parser().getTreeBuilder().reader;
    }

    @Test
    void canLoopOnSelectNext() throws IOException {
        StreamParser streamer = new StreamParser(Parser.htmlParser()).parse(THREE_PARAGRAPHS, "");
        int removedParagraphs = 0;
        Element paragraph;

        while ((paragraph = streamer.selectNext(PARAGRAPH_SELECTOR)) != null) {
            assertEquals(3, paragraph.text().length());
            paragraph.remove();
            removedParagraphs++;
        }

        assertEquals(3, removedParagraphs);
        assertEquals(0, streamer.document().select(PARAGRAPH_SELECTOR).size());
        assertTrue(isClosed(streamer));
    }
}

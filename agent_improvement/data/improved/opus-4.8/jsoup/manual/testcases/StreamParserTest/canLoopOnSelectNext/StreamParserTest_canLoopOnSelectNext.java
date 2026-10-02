package org.jsoup.parser;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StreamParserTest_canLoopOnSelectNext {

    /**
     * Reaching into the StreamParser to check whether its underlying reader has been released. The reader is set to
     * {@code null} once the input has been fully consumed, so a {@code null} reader means the stream is closed.
     */
    private static boolean isClosed(StreamParser streamer) {
        CharacterReader reader = streamer.document().parser().getTreeBuilder().reader;
        return reader == null;
    }

    @Test
    void canLoopOnSelectNext() throws IOException {
        // Three <p> elements, each with a 3-character body: "One", "Two", "Thr".
        StreamParser streamer = new StreamParser(Parser.htmlParser())
            .parse("<div><p>One<p>Two<p>Thr</div>", "");

        // Repeatedly pull the next matching <p>, asserting its body, then remove it from the DOM as we go.
        int matchesFound = 0;
        Element paragraph;
        while ((paragraph = streamer.selectNext("p")) != null) {
            assertEquals(3, paragraph.text().length());
            paragraph.remove();
            matchesFound++;
        }

        assertEquals(3, matchesFound, "should have looped over all three paragraphs");
        assertEquals(0, streamer.document().select("p").size(), "every paragraph was removed during iteration");
        assertTrue(isClosed(streamer), "looping to completion should have read the input to the end and closed it");
    }
}

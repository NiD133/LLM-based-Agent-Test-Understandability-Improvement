package org.jsoup.parser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class StreamParserTest_canLoopOnSelectNext {

    /** Returns true when the StreamParser has consumed all input and closed its reader. */
    static boolean isClosed(StreamParser streamer) {
        return getReader(streamer) == null;
    }

    private static CharacterReader getReader(StreamParser streamer) {
        return streamer.document().parser().getTreeBuilder().reader;
    }

    @Test
    void canLoopOnSelectNext() throws IOException {
        // HTML contains three <p> elements, each with exactly 3 characters of text.
        StreamParser streamer = new StreamParser(Parser.htmlParser())
            .parse("<div><p>One<p>Two<p>Thr</div>", "");

        int matchCount = 0;
        Element paragraph;

        // selectNext("p") advances the parse and returns the next matching element,
        // or null once the entire input has been consumed.
        while ((paragraph = streamer.selectNext("p")) != null) {
            assertEquals(3, paragraph.text().length(), "each paragraph should have 3 characters");
            // Remove the matched element from the DOM while iterating to free memory.
            paragraph.remove();
            matchCount++;
        }

        // All three <p> elements should have been visited.
        assertEquals(3, matchCount);

        // Because every <p> was removed during the loop, none should remain in the document.
        assertEquals(0, streamer.document().select("p").size());

        // Consuming all results via selectNext should cause the reader to be closed automatically.
        assertTrue(isClosed(streamer));
    }
}

package org.jsoup.parser;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StreamParserTest_worksWithXmlParser {
    private static final String XML_WITH_THREE_PARAGRAPHS =
        "<div><p>One</p><p>Two</p><p>Thr</p></div>";
    private static final String BASE_URI = "";
    private static final String PARAGRAPH_SELECTOR = "p";

    @Test
    void worksWithXmlParser() throws IOException {
        StreamParser streamer = new StreamParser(Parser.xmlParser())
            .parse(XML_WITH_THREE_PARAGRAPHS, BASE_URI);

        int paragraphsSeen = removeEachSelectedParagraph(streamer);

        assertEquals(3, paragraphsSeen);
        assertEquals(0, streamer.document().select(PARAGRAPH_SELECTOR).size());
        assertTrue(isClosed(streamer));
    }

    private static int removeEachSelectedParagraph(StreamParser streamer) throws IOException {
        int paragraphsSeen = 0;
        Element paragraph;

        while ((paragraph = streamer.selectNext(PARAGRAPH_SELECTOR)) != null) {
            assertEquals(3, paragraph.text().length());
            paragraph.remove();
            paragraphsSeen++;
        }

        return paragraphsSeen;
    }

    private static boolean isClosed(StreamParser streamer) {
        return getReader(streamer) == null;
    }

    private static CharacterReader getReader(StreamParser streamer) {
        return streamer.document().parser().getTreeBuilder().reader;
    }
}

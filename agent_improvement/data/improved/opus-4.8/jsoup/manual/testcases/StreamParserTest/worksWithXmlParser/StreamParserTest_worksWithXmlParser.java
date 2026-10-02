package org.jsoup.parser;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StreamParserTest_worksWithXmlParser {

    /**
     * Reports whether the StreamParser has consumed all of its input and released the
     * underlying reader. This peeks at the parser's internal reader, which is null once closed.
     */
    private static boolean isClosed(StreamParser streamer) {
        return streamer.document().parser().getTreeBuilder().reader == null;
    }

    @Test
    void worksWithXmlParser() throws IOException {
        // Three <p> elements, each holding a 3-character body, parsed with the XML parser.
        String xml = "<div><p>One</p><p>Two</p><p>Thr</p></div>";
        StreamParser streamer = new StreamParser(Parser.xmlParser()).parse(xml, "");

        // Stream out each <p> as it is parsed, removing it from the document as we go.
        int seenCount = 0;
        Element paragraph;
        while ((paragraph = streamer.selectNext("p")) != null) {
            assertEquals(3, paragraph.text().length());
            paragraph.remove();
            seenCount++;
        }

        assertEquals(3, seenCount);
        // Every <p> was removed during iteration, so none remain in the document.
        assertEquals(0, streamer.document().select("p").size());
        // Reaching the end of the input closes the parser's reader.
        assertTrue(isClosed(streamer));
    }
}

package org.jsoup.parser;

import org.jsoup.helper.DataUtil;
import org.jsoup.integration.ParseTest;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class StreamParserTest_canParseFile {

    /**
     * Verifies that StreamParser can progressively parse a large HTML file using selectNext(),
     * returning the correct last {@code <p>} element and automatically closing the reader
     * once the entire input has been consumed.
     */
    @Test
    void canParseFile() throws IOException {
        File file = ParseTest.getFile("/htmltests/large.html");
        StreamParser streamer = DataUtil.streamParser(file.toPath(), StandardCharsets.UTF_8, "", Parser.htmlParser());

        Element lastParagraph = null;
        Element element;
        while ((element = streamer.selectNext("p")) != null) {
            lastParagraph = element;
        }

        assertTrue(lastParagraph.text().startsWith("VESTIBULUM"));
        // The reader should be automatically closed once the full input has been consumed
        assertTrue(isClosed(streamer));
    }

    /** Returns true if the StreamParser's underlying reader has been closed. */
    static boolean isClosed(StreamParser streamer) {
        return getReader(streamer) == null;
    }

    private static CharacterReader getReader(StreamParser streamer) {
        return streamer.document().parser().getTreeBuilder().reader;
    }
}

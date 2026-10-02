package org.jsoup.parser;

import org.jsoup.helper.DataUtil;
import org.jsoup.integration.ParseTest;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StreamParserTest_canParseFile {

    /**
     * Reaches into the StreamParser's underlying CharacterReader to confirm it has been released.
     * Once the input is fully read, the StreamParser closes the reader, which leaves this back-door
     * accessor returning null.
     */
    private static boolean isReaderClosed(StreamParser streamer) {
        return streamer.document().parser().getTreeBuilder().reader == null;
    }

    @Test
    void canParseFile() throws IOException {
        File largeHtml = ParseTest.getFile("/htmltests/large.html");
        StreamParser streamer = DataUtil.streamParser(
            largeHtml.toPath(), StandardCharsets.UTF_8, "", Parser.htmlParser());

        // Stream through every <p> element until the input is exhausted, keeping the last one seen.
        Element lastParagraph = null;
        Element paragraph;
        while ((paragraph = streamer.selectNext("p")) != null) {
            lastParagraph = paragraph;
        }

        assertNotNull(lastParagraph);
        assertTrue(lastParagraph.text().startsWith("VESTIBULUM"));

        // Reaching the end of the input closes the streamer, which in turn closes the reader.
        assertTrue(isReaderClosed(streamer));
    }
}

package org.jsoup.parser;

import org.jsoup.helper.DataUtil;
import org.jsoup.integration.ParseTest;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StreamParserTest_canParseFile {
    @Test
    void canParseFile() throws IOException {
        File file = ParseTest.getFile("/htmltests/large.html");
        StreamParser streamer = DataUtil.streamParser(file.toPath(), StandardCharsets.UTF_8, "", Parser.htmlParser());

        Element lastParagraph = null;
        Element paragraph;
        while ((paragraph = streamer.selectNext("p")) != null) {
            lastParagraph = paragraph;
        }

        assertTrue(lastParagraph.text().startsWith("VESTIBULUM"));
        assertTrue(isClosed(streamer));
    }

    private static boolean isClosed(StreamParser streamer) {
        return getReader(streamer) == null;
    }

    private static CharacterReader getReader(StreamParser streamer) {
        return streamer.document().parser().getTreeBuilder().reader;
    }
}

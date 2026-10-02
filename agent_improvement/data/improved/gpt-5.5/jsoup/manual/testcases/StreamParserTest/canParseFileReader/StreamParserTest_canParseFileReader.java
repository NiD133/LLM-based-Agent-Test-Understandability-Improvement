package org.jsoup.parser;

import org.jsoup.integration.ParseTest;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StreamParserTest_canParseFileReader {
    @Test
    void canParseFileReader() throws IOException {
        File file = ParseTest.getFile("/htmltests/large.html");

        InputStreamReader utf8Input = new InputStreamReader(
            Files.newInputStream(file.toPath()),
            StandardCharsets.UTF_8
        );
        BufferedReader reader = new BufferedReader(utf8Input);

        StreamParser streamer = new StreamParser(Parser.htmlParser()).parse(reader, file.getAbsolutePath());
        Element lastParagraph = null;
        Element paragraph;
        while ((paragraph = streamer.selectNext("p")) != null) {
            lastParagraph = paragraph;
        }

        assertTrue(lastParagraph.text().startsWith("VESTIBULUM"));
        assertTrue(isClosed(streamer));
        assertThrows(IOException.class, reader::ready);
    }

    private static boolean isClosed(StreamParser streamer) {
        return getReader(streamer) == null;
    }

    private static CharacterReader getReader(StreamParser streamer) {
        return streamer.document().parser().getTreeBuilder().reader;
    }
}

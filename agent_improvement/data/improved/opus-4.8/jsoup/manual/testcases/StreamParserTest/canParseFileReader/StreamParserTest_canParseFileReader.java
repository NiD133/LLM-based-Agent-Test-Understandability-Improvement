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

    /**
     * Verifies that a StreamParser can read from a Reader, and that once the input has been fully
     * consumed the StreamParser closes the underlying Reader automatically.
     */
    @Test
    void canParseFileReader() throws IOException {
        File file = ParseTest.getFile("/htmltests/large.html");
        // Read the file as UTF-8 (FileReader can't specify a charset before Java 11, so use InputStreamReader).
        InputStreamReader fileReader =
            new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(fileReader);

        StreamParser streamer = new StreamParser(Parser.htmlParser()).parse(reader, file.getAbsolutePath());

        // Stream through every <p> element until the input is exhausted, keeping the last one seen.
        Element lastParagraph = null;
        Element paragraph;
        while ((paragraph = streamer.selectNext("p")) != null) {
            lastParagraph = paragraph;
        }
        assertTrue(lastParagraph.text().startsWith("VESTIBULUM"));

        // Reaching the end of the input closes the StreamParser, which in turn closes the Reader.
        assertTrue(isReaderClosed(streamer));

        // A closed BufferedReader rejects ready() with an IOException ("Stream closed").
        assertThrows(IOException.class, reader::ready);
    }

    /**
     * Reports whether the StreamParser has released its underlying CharacterReader, which it does
     * once parsing completes. Reaches into the parser internals as a back door for the assertion.
     */
    private static boolean isReaderClosed(StreamParser streamer) {
        CharacterReader reader = streamer.document().parser().getTreeBuilder().reader;
        return reader == null;
    }
}

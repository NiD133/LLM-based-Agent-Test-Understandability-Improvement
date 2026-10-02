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

import static org.junit.jupiter.api.Assertions.*;

public class StreamParserTest_canParseFileReader {

    /**
     * Returns true if the StreamParser has been closed (its backing CharacterReader is null).
     * Used to verify that the parser auto-closes when the input is fully consumed.
     */
    static boolean isClosed(StreamParser streamer) {
        return getReader(streamer) == null;
    }

    private static CharacterReader getReader(StreamParser streamer) {
        return streamer.document().parser().getTreeBuilder().reader;
    }

    /**
     * Verifies that a StreamParser can parse a large HTML file supplied via a BufferedReader,
     * that it correctly selects all paragraph elements in document order, and that the
     * underlying reader is automatically closed once the entire input has been consumed.
     */
    @Test
    void canParseFileReader() throws IOException {
        File file = ParseTest.getFile("/htmltests/large.html");

        // Java 11+ requires explicit charset on InputStreamReader instead of FileReader
        InputStreamReader input = new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(input);

        StreamParser streamer = new StreamParser(Parser.htmlParser()).parse(reader, file.getAbsolutePath());

        // Iterate through every <p> element, keeping track of the last one seen
        Element lastParagraph = null;
        Element paragraph;
        while ((paragraph = streamer.selectNext("p")) != null) {
            lastParagraph = paragraph;
        }

        // The final paragraph in large.html starts with "VESTIBULUM"
        assertTrue(lastParagraph.text().startsWith("VESTIBULUM"));

        // Exhausting the input must auto-close the streamer and the underlying reader
        assertTrue(isClosed(streamer));
        assertThrows(IOException.class, reader::ready);
    }
}

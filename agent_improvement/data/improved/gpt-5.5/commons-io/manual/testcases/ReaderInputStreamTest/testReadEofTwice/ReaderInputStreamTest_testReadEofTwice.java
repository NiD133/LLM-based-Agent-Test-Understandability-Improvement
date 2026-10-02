package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class ReaderInputStreamTest_testReadEofTwice {

    private static final String INPUT_TEXT = "123";

    @Test
    void testReadEofTwice() throws IOException {
        try (ReaderInputStream readerInputStream = ReaderInputStream.builder()
                .setCharset(StandardCharsets.UTF_8)
                .setReader(new StringReader(INPUT_TEXT))
                .get()) {
            assertEquals('1', readerInputStream.read());
            assertEquals('2', readerInputStream.read());
            assertEquals('3', readerInputStream.read());
            assertEquals(-1, readerInputStream.read());
            assertEquals(-1, readerInputStream.read());
        }
    }
}

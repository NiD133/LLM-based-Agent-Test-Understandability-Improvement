package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class ReaderInputStreamTest_testReadEofTwice {

    private static final int EOF = -1;

    @Test
    void testReadEofTwice() throws IOException {
        // Verify that reading past the end of stream consistently returns EOF on repeated calls
        try (ReaderInputStream stream = ReaderInputStream.builder()
                .setCharset(StandardCharsets.UTF_8)
                .setReader(new StringReader("123"))
                .get()) {
            assertEquals('1', stream.read());
            assertEquals('2', stream.read());
            assertEquals('3', stream.read());
            assertEquals(EOF, stream.read(), "First read past end of stream should return EOF");
            assertEquals(EOF, stream.read(), "Second read past end of stream should also return EOF");
        }
    }
}

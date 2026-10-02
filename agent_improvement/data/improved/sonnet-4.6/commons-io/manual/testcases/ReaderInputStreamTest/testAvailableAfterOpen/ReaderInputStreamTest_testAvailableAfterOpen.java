package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class ReaderInputStreamTest_testAvailableAfterOpen {

    // ISO-8859-1 maps each character to exactly one byte, so byte count == char count.
    private static final String TEST_STRING = "à peine arrivés nous entrâmes dans sa chambre";

    private ReaderInputStream createInputStream() throws IOException {
        return ReaderInputStream.builder()
                .setReader(new StringReader(TEST_STRING))
                .setCharset(StandardCharsets.ISO_8859_1)
                .get();
    }

    @Test
    void testAvailableAfterOpen() throws IOException {
        try (InputStream inputStream = createInputStream()) {
            // Before any read, the internal byte buffer is empty, so available() returns 0.
            assertEquals(0, inputStream.available());

            // The first read() triggers a fill of the internal buffer with all characters
            // from the reader. After consuming one byte, the rest remain buffered.
            inputStream.read();
            assertEquals(TEST_STRING.length() - 1, inputStream.available());
        }
    }
}

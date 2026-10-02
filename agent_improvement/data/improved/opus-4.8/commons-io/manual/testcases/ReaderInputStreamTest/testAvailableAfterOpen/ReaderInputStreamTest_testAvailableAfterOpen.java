package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Tests how {@link ReaderInputStream#available()} behaves after the stream is
 * opened and a single byte has been read.
 */
public class ReaderInputStreamTest_testAvailableAfterOpen {

    /** Text encoded by the stream; ISO-8859-1 maps each character to one byte. */
    private static final String TEST_STRING = "à peine arrivés nous entrâmes dans sa chambre";

    /**
     * Creates a {@link ReaderInputStream} over {@link #TEST_STRING} using the
     * single-byte ISO-8859-1 charset, so one character always yields one byte.
     */
    private ReaderInputStream createInputStream() throws IOException {
        return ReaderInputStream.builder()
                .setReader(new StringReader(TEST_STRING))
                .setCharset(StandardCharsets.ISO_8859_1)
                .get();
    }

    @Test
    void testAvailableAfterOpen() throws IOException {
        try (InputStream inputStream = createInputStream()) {
            // Before anything is read, no bytes have been decoded yet.
            assertEquals(0, inputStream.available());

            // Reading one byte decodes the whole string into the buffer,
            // leaving all but the consumed byte available.
            inputStream.read();
            assertEquals(TEST_STRING.length() - 1, inputStream.available());
        }
    }
}

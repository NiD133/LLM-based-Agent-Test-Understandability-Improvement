package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Tests that reading from a {@link ReaderInputStream} after it has been closed
 * fails with an {@link IOException}.
 */
public class ReaderInputStreamTest_testReadAfterClose {

    private static final String TEST_STRING = "à peine arrivés nous entrâmes dans sa chambre";

    /**
     * Builds a {@link ReaderInputStream} backed by {@link #TEST_STRING} using the ISO-8859-1 charset.
     */
    private ReaderInputStream createInputStream() throws IOException {
        return ReaderInputStream.builder()
                .setReader(new StringReader(TEST_STRING))
                .setCharset(StandardCharsets.ISO_8859_1)
                .get();
    }

    @Test
    void testReadAfterClose() throws IOException {
        try (InputStream inputStream = createInputStream()) {
            inputStream.close();

            // Reading from a closed stream must report an I/O error.
            assertThrows(IOException.class, inputStream::read);
        }
    }
}

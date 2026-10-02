package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class ReaderInputStreamTest_testAvailableAfterClose {

    // A simple non-empty string is sufficient to construct a valid ReaderInputStream
    private static final String TEST_STRING = "à peine arrivés nous entrâmes dans sa chambre";

    private ReaderInputStream createInputStream() throws IOException {
        return ReaderInputStream.builder()
                .setReader(new StringReader(TEST_STRING))
                .setCharset(StandardCharsets.ISO_8859_1)
                .get();
    }

    /**
     * Verifies that calling available() on a closed ReaderInputStream returns 0
     * rather than throwing an exception, consistent with the InputStream contract.
     */
    @Test
    void testAvailableAfterClose() throws IOException {
        try (InputStream inputStream = createInputStream()) {
            inputStream.close();
            assertEquals(0, inputStream.available());
        }
    }
}

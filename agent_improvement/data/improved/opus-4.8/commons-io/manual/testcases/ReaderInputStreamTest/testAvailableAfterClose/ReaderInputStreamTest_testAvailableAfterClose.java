package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link ReaderInputStream#available()} reports no available bytes once the stream has been closed.
 */
public class ReaderInputStreamTest_testAvailableAfterClose {

    /** Sample text encoded by the stream under test. */
    private static final String TEST_STRING = "à peine arrivés nous entrâmes dans sa chambre";

    /**
     * Creates a {@link ReaderInputStream} that encodes {@link #TEST_STRING} using ISO-8859-1.
     */
    private ReaderInputStream createInputStream() throws IOException {
        return ReaderInputStream.builder()
                .setReader(new StringReader(TEST_STRING))
                .setCharset(StandardCharsets.ISO_8859_1)
                .get();
    }

    @Test
    void testAvailableAfterClose() throws IOException {
        try (InputStream inputStream = createInputStream()) {
            inputStream.close();
            assertEquals(0, inputStream.available(), "available() should return 0 once the stream is closed");
        }
    }
}

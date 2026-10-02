package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class ReaderInputStreamTest_testReadZero {

    /**
     * Verifies that read(buf, off, 0) always returns 0 regardless of stream position:
     * once before any data has been consumed, and once after reading all available content.
     */
    private void assertReadZeroReturnsBothBeforeAndAfterContent(
            final String inStr, final ReaderInputStream inputStream) throws IOException {
        final byte[] buf = new byte[30];

        // Reading zero bytes before any content has been consumed must return 0
        assertEquals(0, inputStream.read(buf, 0, 0));

        // Read all available bytes; ASCII string length equals encoded byte length
        assertEquals(inStr.length(), inputStream.read(buf, 0, inStr.length() + 1));

        // Reading zero bytes after content is exhausted must still return 0 (not -1)
        assertEquals(0, inputStream.read(buf, 0, 0));
    }

    @SuppressWarnings("deprecation")
    @Test
    void testReadZero() throws Exception {
        final String input = "test";

        // Test using the deprecated single-argument constructor (uses the platform default charset)
        try (ReaderInputStream stream = new ReaderInputStream(new StringReader(input))) {
            assertReadZeroReturnsBothBeforeAndAfterContent(input, stream);
        }

        // Test using the preferred builder API
        try (ReaderInputStream stream = ReaderInputStream.builder()
                .setReader(new StringReader(input))
                .get()) {
            assertReadZeroReturnsBothBeforeAndAfterContent(input, stream);
        }
    }
}

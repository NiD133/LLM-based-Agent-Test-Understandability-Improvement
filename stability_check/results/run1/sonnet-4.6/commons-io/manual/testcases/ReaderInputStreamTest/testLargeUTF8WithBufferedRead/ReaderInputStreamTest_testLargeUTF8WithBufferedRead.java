package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

public class ReaderInputStreamTest_testLargeUTF8WithBufferedRead {

    private static final String UTF_8 = StandardCharsets.UTF_8.name();

    // A French sentence containing multi-byte UTF-8 characters (accented letters)
    private static final String TEST_STRING = "à peine arrivés nous entrâmes dans sa chambre";

    // Repeated 100 times to exercise reading across many internal buffer refills
    private static final String LARGE_TEST_STRING = StringUtils.repeat(TEST_STRING, 100);

    private final Random random = new Random();

    /**
     * Reads bytes from {@code in} using randomly sized and randomly offset sub-regions of a
     * scratch buffer, and asserts that every byte matches the corresponding byte in
     * {@code expected}.  This exercises the stream's partial-read contract under varied
     * alignment conditions.
     */
    private void testWithBufferedRead(final byte[] expected, final ReaderInputStream in) throws IOException {
        final byte[] scratchBuffer = new byte[128];
        int expectedIndex = 0;

        while (true) {
            // Choose a random intra-buffer start position and read length for each call
            int readOffset = random.nextInt(64);
            final int readLength = random.nextInt(64);

            int bytesRead = in.read(scratchBuffer, readOffset, readLength);

            if (bytesRead == -1) {
                // Stream exhausted: verify all expected bytes have been consumed
                assertEquals(expectedIndex, expected.length);
                break;
            }

            // The stream must not return more bytes than were requested
            assertTrue(bytesRead <= readLength);

            // Verify each byte that was read matches the expected sequence
            while (bytesRead > 0) {
                assertTrue(expectedIndex < expected.length);
                assertEquals(expected[expectedIndex], scratchBuffer[readOffset]);
                expectedIndex++;
                readOffset++;
                bytesRead--;
            }
        }
    }

    /**
     * Encodes {@code testString} to bytes using {@code charsetName}, then verifies that a
     * {@link ReaderInputStream} wrapping the same string produces an identical byte sequence
     * when read through randomly aligned buffer windows.  Both the deprecated constructor and
     * the builder API are exercised.
     */
    private void testWithBufferedRead(final String testString, final String charsetName) throws IOException {
        final byte[] expectedBytes = testString.getBytes(charsetName);

        try (ReaderInputStream in = new ReaderInputStream(new StringReader(testString), charsetName)) {
            testWithBufferedRead(expectedBytes, in);
        }

        try (ReaderInputStream in = ReaderInputStream.builder()
                .setReader(new StringReader(testString))
                .setCharset(charsetName)
                .get()) {
            testWithBufferedRead(expectedBytes, in);
        }
    }

    @Test
    void testLargeUTF8WithBufferedRead() throws IOException {
        testWithBufferedRead(LARGE_TEST_STRING, UTF_8);
    }
}

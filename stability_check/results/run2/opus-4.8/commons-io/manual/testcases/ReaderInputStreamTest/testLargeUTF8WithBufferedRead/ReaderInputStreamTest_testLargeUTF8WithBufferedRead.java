package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link ReaderInputStream} correctly encodes a large UTF-8 string when it is
 * consumed through {@code read(byte[], off, len)} calls that use randomly varying offsets
 * and lengths (i.e. buffered reads that do not consume the stream in a single, uniform pass).
 */
public class ReaderInputStreamTest_testLargeUTF8WithBufferedRead {

    private static final String UTF_8 = StandardCharsets.UTF_8.name();

    /** A short phrase containing multi-byte UTF-8 characters. */
    private static final String TEST_STRING = "à peine arrivés nous entrâmes dans sa chambre";

    /** A large input built by repeating {@link #TEST_STRING} many times. */
    private static final String LARGE_TEST_STRING = StringUtils.repeat(TEST_STRING, 100);

    private final Random random = new Random();

    /**
     * Consumes the given stream using {@code read(byte[], off, len)} calls with random offsets and
     * lengths, verifying that the bytes produced match {@code expected} exactly and in order.
     *
     * @param expected the full sequence of bytes the stream is expected to produce.
     * @param in       the stream under test.
     */
    private void assertStreamMatchesExpected(final byte[] expected, final ReaderInputStream in) throws IOException {
        final byte[] buffer = new byte[128];
        int expectedIndex = 0;
        while (true) {
            // Use random (but valid) offset/length for each read to exercise buffered reads.
            int destOffset = random.nextInt(64);
            final int requestedLength = random.nextInt(64);
            int bytesRead = in.read(buffer, destOffset, requestedLength);

            if (bytesRead == -1) {
                // End of stream: every expected byte must have been read.
                assertEquals(expectedIndex, expected.length);
                break;
            }

            assertTrue(bytesRead <= requestedLength);

            // Verify each byte just read matches the next expected byte.
            while (bytesRead > 0) {
                assertTrue(expectedIndex < expected.length);
                assertEquals(expected[expectedIndex], buffer[destOffset]);
                expectedIndex++;
                destOffset++;
                bytesRead--;
            }
        }
    }

    /**
     * Encodes {@code testString} with the given charset and verifies the result via buffered reads,
     * once using the deprecated constructor and once using the builder API.
     */
    private void assertBufferedReadRoundTrips(final String testString, final String charsetName) throws IOException {
        final byte[] expected = testString.getBytes(charsetName);

        try (ReaderInputStream in = new ReaderInputStream(new StringReader(testString), charsetName)) {
            assertStreamMatchesExpected(expected, in);
        }
        try (ReaderInputStream in = ReaderInputStream.builder()
                .setReader(new StringReader(testString))
                .setCharset(charsetName)
                .get()) {
            assertStreamMatchesExpected(expected, in);
        }
    }

    @Test
    void testLargeUTF8WithBufferedRead() throws IOException {
        assertBufferedReadRoundTrips(LARGE_TEST_STRING, UTF_8);
    }
}

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
 * Verifies that {@link ReaderInputStream} produces exactly the expected UTF-8 bytes
 * for a large input string, even when the caller reads into a byte array using
 * randomized offsets and lengths.
 */
public class ReaderInputStreamTest_testLargeUTF8WithBufferedRead {

    private static final String UTF_8 = StandardCharsets.UTF_8.name();

    /** A short phrase containing non-ASCII characters that require multi-byte UTF-8 encoding. */
    private static final String TEST_STRING = "à peine arrivés nous entrâmes dans sa chambre";

    /** A large input built by repeating {@link #TEST_STRING} 100 times. */
    private static final String LARGE_TEST_STRING = StringUtils.repeat(TEST_STRING, 100);

    private final Random random = new Random();

    /**
     * Drains the given stream using randomized (offset, length) read requests and asserts
     * that the bytes returned match {@code expected} in order.
     */
    private void assertStreamMatchesUsingBufferedReads(final byte[] expected, final ReaderInputStream in) throws IOException {
        final byte[] buffer = new byte[128];
        int expectedIndex = 0;
        while (true) {
            // Use random offsets and lengths so we exercise partial reads into arbitrary buffer positions.
            int bufferOffset = random.nextInt(64);
            final int requestedLength = random.nextInt(64);
            int bytesRead = in.read(buffer, bufferOffset, requestedLength);
            if (bytesRead == -1) {
                // End of stream: every expected byte must have been consumed.
                assertEquals(expectedIndex, expected.length);
                break;
            }
            assertTrue(bytesRead <= requestedLength);
            // Compare each byte just read against the next expected byte.
            while (bytesRead > 0) {
                assertTrue(expectedIndex < expected.length);
                assertEquals(expected[expectedIndex], buffer[bufferOffset]);
                expectedIndex++;
                bufferOffset++;
                bytesRead--;
            }
        }
    }

    /**
     * Encodes {@code testString} with the given charset and verifies that both the constructor-based
     * and builder-based {@link ReaderInputStream} instances reproduce the same bytes.
     */
    private void assertBufferedReadReproducesBytes(final String testString, final String charsetName) throws IOException {
        final byte[] expected = testString.getBytes(charsetName);
        try (ReaderInputStream in = new ReaderInputStream(new StringReader(testString), charsetName)) {
            assertStreamMatchesUsingBufferedReads(expected, in);
        }
        try (ReaderInputStream in = ReaderInputStream.builder().setReader(new StringReader(testString)).setCharset(charsetName).get()) {
            assertStreamMatchesUsingBufferedReads(expected, in);
        }
    }

    @Test
    void testLargeUTF8WithBufferedRead() throws IOException {
        assertBufferedReadReproducesBytes(LARGE_TEST_STRING, UTF_8);
    }
}

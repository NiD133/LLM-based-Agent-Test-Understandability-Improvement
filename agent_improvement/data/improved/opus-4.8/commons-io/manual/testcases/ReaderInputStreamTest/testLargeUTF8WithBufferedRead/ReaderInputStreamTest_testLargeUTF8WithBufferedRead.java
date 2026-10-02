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
 * Tests that {@link ReaderInputStream} correctly encodes a large UTF-8 string when its
 * {@code read(byte[], off, len)} method is called repeatedly with randomly varying offsets
 * and lengths (a "buffered read" access pattern).
 */
public class ReaderInputStreamTest_testLargeUTF8WithBufferedRead {

    private static final String UTF_8 = StandardCharsets.UTF_8.name();

    /** A short phrase containing non-ASCII (accented) characters, so UTF-8 encoding spans multiple bytes per char. */
    private static final String TEST_STRING = "à peine arrivés nous entrâmes dans sa chambre";

    /** A long input built by repeating {@link #TEST_STRING}, so the encoder buffer is refilled many times. */
    private static final String LARGE_TEST_STRING = StringUtils.repeat(TEST_STRING, 100);

    private final Random random = new Random();

    /**
     * Drains the given stream using randomly sized chunked reads and verifies that every byte
     * produced matches the expected UTF-8 encoding, in order.
     *
     * <p>Each call reads into a random offset of a scratch buffer with a random length. Because
     * both values are in {@code [0, 64)}, the read region always stays within the 128-byte buffer.</p>
     *
     * @param expected the full expected byte sequence.
     * @param in       the stream under test.
     */
    private void assertStreamEncodesTo(final byte[] expected, final ReaderInputStream in) throws IOException {
        final byte[] readBuffer = new byte[128];
        int expectedIndex = 0;
        while (true) {
            final int readOffset = random.nextInt(64);
            final int requestedLength = random.nextInt(64);
            int bytesRead = in.read(readBuffer, readOffset, requestedLength);

            // A return of -1 signals end of stream; by then we must have consumed every expected byte.
            if (bytesRead == -1) {
                assertEquals(expectedIndex, expected.length);
                break;
            }

            // The stream may return fewer bytes than requested, but never more.
            assertTrue(bytesRead <= requestedLength);

            // Verify each freshly read byte matches the next expected byte.
            int bufferIndex = readOffset;
            while (bytesRead > 0) {
                assertTrue(expectedIndex < expected.length);
                assertEquals(expected[expectedIndex], readBuffer[bufferIndex]);
                expectedIndex++;
                bufferIndex++;
                bytesRead--;
            }
        }
    }

    /**
     * Encodes {@code testString} with the given charset two ways and checks both produce the
     * expected bytes under the chunked-read pattern: once via the deprecated constructor and once
     * via the {@link ReaderInputStream.Builder}.
     */
    private void assertBufferedReadMatches(final String testString, final String charsetName) throws IOException {
        final byte[] expected = testString.getBytes(charsetName);

        try (ReaderInputStream in = new ReaderInputStream(new StringReader(testString), charsetName)) {
            assertStreamEncodesTo(expected, in);
        }
        try (ReaderInputStream in = ReaderInputStream.builder()
                .setReader(new StringReader(testString))
                .setCharset(charsetName)
                .get()) {
            assertStreamEncodesTo(expected, in);
        }
    }

    @Test
    void testLargeUTF8WithBufferedRead() throws IOException {
        assertBufferedReadMatches(LARGE_TEST_STRING, UTF_8);
    }
}

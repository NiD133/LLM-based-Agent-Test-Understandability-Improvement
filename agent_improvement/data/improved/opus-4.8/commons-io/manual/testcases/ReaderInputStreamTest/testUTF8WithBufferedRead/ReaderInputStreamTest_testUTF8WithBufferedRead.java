package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link ReaderInputStream} produces the correct UTF-8 byte sequence
 * when its data is consumed through the bulk {@code read(byte[], off, len)} method
 * using randomly sized, randomly positioned read requests.
 */
public class ReaderInputStreamTest_testUTF8WithBufferedRead {

    private static final String UTF_8 = StandardCharsets.UTF_8.name();

    /** Sample French text containing accented characters that encode to multiple bytes in UTF-8. */
    private static final String TEST_STRING = "à peine arrivés nous entrâmes dans sa chambre";

    private final Random random = new Random();

    /**
     * Drains the given stream using bulk reads with random offsets and lengths,
     * asserting that every byte produced matches the expected encoded bytes in order.
     *
     * @param expected the full sequence of bytes the stream is expected to produce.
     * @param in       the stream under test.
     */
    private void assertStreamProducesBytes(final byte[] expected, final ReaderInputStream in) throws IOException {
        final byte[] readBuffer = new byte[128];
        // Index of the next expected byte we have yet to verify.
        int expectedIndex = 0;
        while (true) {
            // Use random (but in-bounds) destination offsets and lengths to exercise
            // the bulk read across many different argument combinations.
            int destOffset = random.nextInt(64);
            final int requestedLength = random.nextInt(64);
            int bytesRead = in.read(readBuffer, destOffset, requestedLength);
            if (bytesRead == -1) {
                // End of stream: we must have consumed exactly all expected bytes.
                assertEquals(expectedIndex, expected.length);
                break;
            }
            // A read must never return more bytes than were requested.
            assertTrue(bytesRead <= requestedLength);
            // Verify each byte just read matches the next expected byte.
            while (bytesRead > 0) {
                assertTrue(expectedIndex < expected.length);
                assertEquals(expected[expectedIndex], readBuffer[destOffset]);
                expectedIndex++;
                destOffset++;
                bytesRead--;
            }
        }
    }

    /**
     * Encodes {@code testString} with {@code charsetName} and verifies that a
     * {@link ReaderInputStream} reproduces the same bytes, using both the deprecated
     * constructor and the builder API.
     */
    private void assertBufferedReadRoundTrips(final String testString, final String charsetName) throws IOException {
        final byte[] expected = testString.getBytes(charsetName);
        // Verify the stream created via the (deprecated) constructor.
        try (ReaderInputStream in = new ReaderInputStream(new StringReader(testString), charsetName)) {
            assertStreamProducesBytes(expected, in);
        }
        // Verify the stream created via the builder API.
        try (ReaderInputStream in = ReaderInputStream.builder()
                .setReader(new StringReader(testString))
                .setCharset(charsetName)
                .get()) {
            assertStreamProducesBytes(expected, in);
        }
    }

    @Test
    void testUTF8WithBufferedRead() throws IOException {
        assertBufferedReadRoundTrips(TEST_STRING, UTF_8);
    }
}

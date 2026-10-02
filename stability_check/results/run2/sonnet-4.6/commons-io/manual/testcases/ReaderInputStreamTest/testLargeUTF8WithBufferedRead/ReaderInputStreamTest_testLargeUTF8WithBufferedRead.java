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

    private static final String TEST_STRING = "à peine arrivés nous entrâmes dans sa chambre";

    private static final String LARGE_TEST_STRING = StringUtils.repeat(TEST_STRING, 100);

    /** Size of the scratch buffer used during buffered reads. */
    private static final int SCRATCH_BUFFER_SIZE = 128;

    /** Upper bound (exclusive) for randomly chosen read offsets and lengths within the scratch buffer. */
    private static final int MAX_READ_OFFSET_OR_LENGTH = 64;

    private final Random random = new Random();

    /**
     * Reads all bytes from {@code in} using randomly chosen offsets and lengths within a scratch
     * buffer, and verifies byte-by-byte that the output matches {@code expected}.
     *
     * The random parameters exercise the full {@code read(byte[], off, len)} contract: each call
     * may return fewer bytes than requested, so the inner loop advances through the scratch buffer
     * until the returned count is exhausted before moving to the next read.
     */
    private void assertBufferedReadProducesExpectedBytes(final byte[] expected, final ReaderInputStream in) throws IOException {
        final byte[] scratchBuffer = new byte[SCRATCH_BUFFER_SIZE];
        int expectedIndex = 0;
        while (true) {
            int readOffset = random.nextInt(MAX_READ_OFFSET_OR_LENGTH);
            final int readLength = random.nextInt(MAX_READ_OFFSET_OR_LENGTH);
            int bytesRead = in.read(scratchBuffer, readOffset, readLength);
            if (bytesRead == -1) {
                assertEquals(expectedIndex, expected.length);
                break;
            }
            assertTrue(bytesRead <= readLength);
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
     * Verifies that a {@link ReaderInputStream} over {@code testString} encoded as
     * {@code charsetName} produces the expected bytes when read via random-offset buffered reads.
     * Both the deprecated constructor and the builder API are exercised.
     */
    private void assertLargeStringRoundTripsViaBufferedRead(final String testString, final String charsetName) throws IOException {
        final byte[] expected = testString.getBytes(charsetName);

        try (ReaderInputStream in = new ReaderInputStream(new StringReader(testString), charsetName)) {
            assertBufferedReadProducesExpectedBytes(expected, in);
        }
        try (ReaderInputStream in = ReaderInputStream.builder()
                .setReader(new StringReader(testString))
                .setCharset(charsetName)
                .get()) {
            assertBufferedReadProducesExpectedBytes(expected, in);
        }
    }

    @Test
    void testLargeUTF8WithBufferedRead() throws IOException {
        assertLargeStringRoundTripsViaBufferedRead(LARGE_TEST_STRING, UTF_8);
    }
}

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

    // A multi-byte UTF-8 string (contains Latin characters with diacritics)
    private static final String TEST_STRING = "à peine arrivés nous entrâmes dans sa chambre";

    // Repeat to exercise the stream over a large input
    private static final String LARGE_TEST_STRING = StringUtils.repeat(TEST_STRING, 100);

    private static final String UTF_8 = StandardCharsets.UTF_8.name();

    // Buffer large enough to hold a full read cycle; offset + length must not exceed this
    private static final int READ_BUFFER_SIZE = 128;

    // Upper bound for random offset and length, both must stay within READ_BUFFER_SIZE
    private static final int MAX_RANDOM_BOUND = 64;

    private final Random random = new Random();

    /**
     * Reads the entire stream using random sub-array reads (random offset and length each
     * iteration) and verifies that every byte matches the {@code expected} array in order.
     */
    private void testWithBufferedRead(final byte[] expected, final ReaderInputStream in) throws IOException {
        final byte[] buffer = new byte[READ_BUFFER_SIZE];
        int totalBytesVerified = 0;

        while (true) {
            int bufferOffset = random.nextInt(MAX_RANDOM_BOUND);
            final int bufferLength = random.nextInt(MAX_RANDOM_BOUND);
            int read = in.read(buffer, bufferOffset, bufferLength);

            if (read == -1) {
                // End of stream: we must have consumed every expected byte
                assertEquals(totalBytesVerified, expected.length);
                break;
            }

            assertTrue(read <= bufferLength, "read() must not return more bytes than requested");

            // Verify each byte returned in this read against the expected sequence
            while (read > 0) {
                assertTrue(totalBytesVerified < expected.length, "Stream produced more bytes than expected");
                assertEquals(expected[totalBytesVerified], buffer[bufferOffset],
                        "Byte mismatch at position " + totalBytesVerified);
                totalBytesVerified++;
                bufferOffset++;
                read--;
            }
        }
    }

    /**
     * Exercises both the legacy constructor and the builder API with the same input and
     * verifies that both produce identical byte sequences.
     */
    private void testWithBufferedRead(final String testString, final String charsetName) throws IOException {
        final byte[] expected = testString.getBytes(charsetName);

        // Legacy constructor path
        try (ReaderInputStream in = new ReaderInputStream(new StringReader(testString), charsetName)) {
            testWithBufferedRead(expected, in);
        }

        // Builder API path
        try (ReaderInputStream in = ReaderInputStream.builder()
                .setReader(new StringReader(testString))
                .setCharset(charsetName)
                .get()) {
            testWithBufferedRead(expected, in);
        }
    }

    @Test
    void testLargeUTF8WithBufferedRead() throws IOException {
        testWithBufferedRead(LARGE_TEST_STRING, UTF_8);
    }
}

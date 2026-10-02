package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import org.junit.jupiter.api.Test;

public class ReaderInputStreamTest_testUTF8WithBufferedRead {

    private static final String UTF_8 = StandardCharsets.UTF_8.name();

    private static final String TEST_STRING = "à peine arrivés nous entrâmes dans sa chambre";

    private static final int READ_BUFFER_SIZE = 128;
    private static final int MAX_RANDOM_BOUND = 64;

    private final Random random = new Random();

    /**
     * Reads from the stream using random offsets and lengths, verifying each byte matches the expected array.
     * This simulates real-world partial-read scenarios to confirm the stream encodes correctly regardless of
     * how many bytes are requested at a time.
     */
    private void assertBufferedReadMatchesExpected(final byte[] expected, final ReaderInputStream in) throws IOException {
        final byte[] buffer = new byte[READ_BUFFER_SIZE];
        int expectedIndex = 0;
        while (true) {
            int bufferOffset = random.nextInt(MAX_RANDOM_BOUND);
            final int readLength = random.nextInt(MAX_RANDOM_BOUND);
            int bytesRead = in.read(buffer, bufferOffset, readLength);
            if (bytesRead == -1) {
                assertEquals(expectedIndex, expected.length, "Stream ended before all expected bytes were consumed");
                break;
            }
            assertTrue(bytesRead <= readLength, "Read returned more bytes than the requested length");
            while (bytesRead > 0) {
                assertTrue(expectedIndex < expected.length, "Read past end of expected byte array");
                assertEquals(expected[expectedIndex], buffer[bufferOffset],
                        "Byte mismatch at index " + expectedIndex);
                expectedIndex++;
                bufferOffset++;
                bytesRead--;
            }
        }
    }

    private void assertBufferedReadRoundtrip(final String testString, final String charsetName) throws IOException {
        final byte[] expected = testString.getBytes(charsetName);
        try (ReaderInputStream in = new ReaderInputStream(new StringReader(testString), charsetName)) {
            assertBufferedReadMatchesExpected(expected, in);
        }
        try (ReaderInputStream in = ReaderInputStream.builder()
                .setReader(new StringReader(testString))
                .setCharset(charsetName)
                .get()) {
            assertBufferedReadMatchesExpected(expected, in);
        }
    }

    @Test
    void testUTF8WithBufferedRead() throws IOException {
        assertBufferedReadRoundtrip(TEST_STRING, UTF_8);
    }
}

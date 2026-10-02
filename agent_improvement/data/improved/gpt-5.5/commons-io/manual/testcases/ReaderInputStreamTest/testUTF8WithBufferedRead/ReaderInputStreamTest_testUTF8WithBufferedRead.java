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

    private static final String TEST_STRING = "\u00e0 peine arriv\u00e9s nous entr\u00e2mes dans sa chambre";

    private static final int OUTPUT_BUFFER_SIZE = 128;

    private static final int MAX_RANDOM_BUFFER_OFFSET = 64;

    private static final int MAX_RANDOM_READ_LENGTH = 64;

    private final Random random = new Random();

    private void assertBufferedReadsMatchExpectedBytes(final byte[] expected, final ReaderInputStream input) throws IOException {
        final byte[] actualBuffer = new byte[OUTPUT_BUFFER_SIZE];
        int expectedOffset = 0;

        while (true) {
            int actualBufferOffset = random.nextInt(MAX_RANDOM_BUFFER_OFFSET);
            final int requestedLength = random.nextInt(MAX_RANDOM_READ_LENGTH);
            int bytesRead = input.read(actualBuffer, actualBufferOffset, requestedLength);

            if (bytesRead == -1) {
                assertEquals(expected.length, expectedOffset);
                break;
            }

            assertTrue(bytesRead <= requestedLength);
            while (bytesRead > 0) {
                assertTrue(expectedOffset < expected.length);
                assertEquals(expected[expectedOffset], actualBuffer[actualBufferOffset]);
                expectedOffset++;
                actualBufferOffset++;
                bytesRead--;
            }
        }
    }

    private void assertBufferedReadsEncodeText(final String testString, final String charsetName) throws IOException {
        final byte[] expected = testString.getBytes(charsetName);

        try (ReaderInputStream input = new ReaderInputStream(new StringReader(testString), charsetName)) {
            assertBufferedReadsMatchExpectedBytes(expected, input);
        }

        try (ReaderInputStream input = ReaderInputStream.builder().setReader(new StringReader(testString)).setCharset(charsetName).get()) {
            assertBufferedReadsMatchExpectedBytes(expected, input);
        }
    }

    @Test
    void testUTF8WithBufferedRead() throws IOException {
        assertBufferedReadsEncodeText(TEST_STRING, UTF_8);
    }
}

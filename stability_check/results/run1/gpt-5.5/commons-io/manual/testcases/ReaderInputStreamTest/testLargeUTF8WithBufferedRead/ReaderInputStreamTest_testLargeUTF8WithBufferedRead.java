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

    private static final String TEST_STRING = "\u00e0 peine arriv\u00e9s nous entr\u00e2mes dans sa chambre";

    private static final String LARGE_TEST_STRING = StringUtils.repeat(TEST_STRING, 100);

    private static final int READ_BUFFER_SIZE = 128;

    private static final int MAX_RANDOM_BUFFER_OFFSET = 64;

    private static final int MAX_RANDOM_READ_LENGTH = 64;

    private final Random random = new Random();

    private void assertBufferedReadMatches(final byte[] expected, final ReaderInputStream in) throws IOException {
        final byte[] buffer = new byte[READ_BUFFER_SIZE];
        int offset = 0;

        while (true) {
            int bufferOffset = random.nextInt(MAX_RANDOM_BUFFER_OFFSET);
            final int bufferLength = random.nextInt(MAX_RANDOM_READ_LENGTH);
            int read = in.read(buffer, bufferOffset, bufferLength);

            if (read == -1) {
                assertEquals(offset, expected.length);
                break;
            }

            assertTrue(read <= bufferLength);
            while (read > 0) {
                assertTrue(offset < expected.length);
                assertEquals(expected[offset], buffer[bufferOffset]);
                offset++;
                bufferOffset++;
                read--;
            }
        }
    }

    private void assertBufferedReadMatches(final String testString, final String charsetName) throws IOException {
        final byte[] expected = testString.getBytes(charsetName);

        try (ReaderInputStream in = new ReaderInputStream(new StringReader(testString), charsetName)) {
            assertBufferedReadMatches(expected, in);
        }

        try (ReaderInputStream in = ReaderInputStream.builder().setReader(new StringReader(testString)).setCharset(charsetName).get()) {
            assertBufferedReadMatches(expected, in);
        }
    }

    @Test
    void testLargeUTF8WithBufferedRead() throws IOException {
        assertBufferedReadMatches(LARGE_TEST_STRING, UTF_8);
    }
}

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

    private static final int BUFFER_SIZE = 128;
    private static final int MAX_RANDOM_BUFFER_OFFSET = 64;
    private static final int MAX_RANDOM_READ_LENGTH = 64;

    private final Random random = new Random();

    private void assertBufferedReadsMatch(final byte[] expectedBytes, final ReaderInputStream inputStream) throws IOException {
        final byte[] buffer = new byte[BUFFER_SIZE];
        int expectedOffset = 0;

        while (true) {
            int bufferOffset = random.nextInt(MAX_RANDOM_BUFFER_OFFSET);
            final int bufferLength = random.nextInt(MAX_RANDOM_READ_LENGTH);
            int bytesRead = inputStream.read(buffer, bufferOffset, bufferLength);

            if (bytesRead == -1) {
                assertEquals(expectedOffset, expectedBytes.length);
                break;
            }

            assertTrue(bytesRead <= bufferLength);
            while (bytesRead > 0) {
                assertTrue(expectedOffset < expectedBytes.length);
                assertEquals(expectedBytes[expectedOffset], buffer[bufferOffset]);
                expectedOffset++;
                bufferOffset++;
                bytesRead--;
            }
        }
    }

    private void assertBufferedReadsMatch(final String testString, final String charsetName) throws IOException {
        final byte[] expectedBytes = testString.getBytes(charsetName);

        try (ReaderInputStream inputStream = new ReaderInputStream(new StringReader(testString), charsetName)) {
            assertBufferedReadsMatch(expectedBytes, inputStream);
        }

        try (ReaderInputStream inputStream = ReaderInputStream.builder().setReader(new StringReader(testString)).setCharset(charsetName).get()) {
            assertBufferedReadsMatch(expectedBytes, inputStream);
        }
    }

    @Test
    void testLargeUTF8WithBufferedRead() throws IOException {
        assertBufferedReadsMatch(LARGE_TEST_STRING, UTF_8);
    }
}

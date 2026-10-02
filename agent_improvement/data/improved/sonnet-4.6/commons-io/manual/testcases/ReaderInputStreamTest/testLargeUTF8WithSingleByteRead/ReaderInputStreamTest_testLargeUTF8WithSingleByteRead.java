package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

public class ReaderInputStreamTest_testLargeUTF8WithSingleByteRead {

    private static final String UTF_8 = StandardCharsets.UTF_8.name();

    // A string containing multi-byte UTF-8 characters (e.g. accented letters).
    private static final String TEST_STRING = "à peine arrivés nous entrâmes dans sa chambre";

    // Repeat the base string 100 times to exercise buffering across many encoding cycles.
    private static final String LARGE_TEST_STRING = StringUtils.repeat(TEST_STRING, 100);

    /**
     * Reads every byte from the stream one at a time and verifies that each byte
     * matches the expected UTF-8 encoding of the source string. After all bytes
     * are consumed, a final read must return -1 (EOF).
     */
    private void testWithSingleByteRead(final String testString, final String charsetName) throws IOException {
        final byte[] expectedBytes = testString.getBytes(charsetName);
        try (ReaderInputStream in = new ReaderInputStream(new StringReader(testString), charsetName)) {
            for (final byte expectedByte : expectedBytes) {
                final int read = in.read();
                assertTrue(read >= 0, "Expected a non-negative byte value but got: " + read);
                assertTrue(read <= 255, "Byte value must be in unsigned range [0, 255] but got: " + read);
                assertEquals(expectedByte, (byte) read, "Byte value mismatch during single-byte read");
            }
            assertEquals(-1, in.read(), "Expected EOF (-1) after all bytes were consumed");
        }
    }

    @Test
    void testLargeUTF8WithSingleByteRead() throws IOException {
        testWithSingleByteRead(LARGE_TEST_STRING, UTF_8);
    }
}

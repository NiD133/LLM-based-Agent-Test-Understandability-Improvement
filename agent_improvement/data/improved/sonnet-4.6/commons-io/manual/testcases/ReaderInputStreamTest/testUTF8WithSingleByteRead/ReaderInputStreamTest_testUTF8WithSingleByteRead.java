package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class ReaderInputStreamTest_testUTF8WithSingleByteRead {

    private static final String UTF_8 = StandardCharsets.UTF_8.name();

    // French text containing multi-byte UTF-8 characters (e.g. à, é, â)
    private static final String TEST_STRING = "à peine arrivés nous entrâmes dans sa chambre";

    /**
     * Reads the stream one byte at a time and verifies each byte matches the
     * expected UTF-8 encoding of the source string, then confirms EOF is returned.
     */
    private void testWithSingleByteRead(final String testString, final String charsetName) throws IOException {
        final byte[] expectedBytes = testString.getBytes(charsetName);
        try (ReaderInputStream in = new ReaderInputStream(new StringReader(testString), charsetName)) {
            for (int i = 0; i < expectedBytes.length; i++) {
                final int read = in.read();
                assertTrue(read >= 0 && read <= 255, "Byte at index " + i + " should be in the unsigned byte range [0, 255]");
                assertEquals(expectedBytes[i], (byte) read, "Byte at index " + i + " should match the UTF-8 encoding");
            }
            assertEquals(-1, in.read(), "Stream should return -1 (EOF) after all bytes have been read");
        }
    }

    @Test
    void testUTF8WithSingleByteRead() throws IOException {
        testWithSingleByteRead(TEST_STRING, UTF_8);
    }
}

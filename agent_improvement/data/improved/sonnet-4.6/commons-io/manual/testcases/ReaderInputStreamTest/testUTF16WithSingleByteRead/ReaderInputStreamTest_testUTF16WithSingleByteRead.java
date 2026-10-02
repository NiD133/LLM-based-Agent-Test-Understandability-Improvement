package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class ReaderInputStreamTest_testUTF16WithSingleByteRead {

    private static final String UTF_16 = StandardCharsets.UTF_16.name();

    // A string with non-ASCII characters to exercise multi-byte UTF-16 encoding
    private static final String TEST_STRING = "à peine arrivés nous entrâmes dans sa chambre";

    /**
     * Reads the given string through a ReaderInputStream one byte at a time using the specified
     * charset, and asserts that each byte matches the expected encoding of the string.
     */
    private void testWithSingleByteRead(final String testString, final String charsetName) throws IOException {
        final byte[] expectedBytes = testString.getBytes(charsetName);
        try (ReaderInputStream in = new ReaderInputStream(new StringReader(testString), charsetName)) {
            for (int i = 0; i < expectedBytes.length; i++) {
                final int byteRead = in.read();
                assertTrue(byteRead >= 0, "Byte at index " + i + " must be non-negative (not EOF)");
                assertTrue(byteRead <= 255, "Byte at index " + i + " must fit in an unsigned byte (0-255)");
                assertEquals(expectedBytes[i], (byte) byteRead,
                        "Byte at index " + i + " must match the expected UTF-16 encoding");
            }
            assertEquals(-1, in.read(), "Stream must signal EOF after all bytes are consumed");
        }
    }

    @Test
    void testUTF16WithSingleByteRead() throws IOException {
        testWithSingleByteRead(TEST_STRING, UTF_16);
    }
}

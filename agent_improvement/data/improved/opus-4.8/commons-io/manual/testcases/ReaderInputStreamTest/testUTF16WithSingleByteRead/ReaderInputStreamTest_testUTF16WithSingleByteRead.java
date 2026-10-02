package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Tests that reading a {@link ReaderInputStream} one byte at a time (via {@link ReaderInputStream#read()})
 * reproduces exactly the UTF-16 encoded bytes of the source string, followed by end-of-stream.
 */
public class ReaderInputStreamTest_testUTF16WithSingleByteRead {

    private static final String UTF_16 = StandardCharsets.UTF_16.name();

    /** A string containing non-ASCII (accented) characters so the encoding produces multi-byte sequences. */
    private static final String TEST_STRING = "à peine arrivés nous entrâmes dans sa chambre";

    @Test
    void testUTF16WithSingleByteRead() throws IOException {
        final byte[] expectedBytes = TEST_STRING.getBytes(UTF_16);

        try (ReaderInputStream in = new ReaderInputStream(new StringReader(TEST_STRING), UTF_16)) {
            // Each single-byte read() must return the next encoded byte as an unsigned value (0..255).
            for (final byte expectedByte : expectedBytes) {
                final int actual = in.read();
                assertTrue(actual >= 0, "read() should return a non-negative byte value");
                assertTrue(actual <= 255, "read() should return an unsigned byte value (<= 255)");
                assertEquals(expectedByte, (byte) actual);
            }
            // The stream is fully consumed, so the next read() signals end-of-stream.
            assertEquals(-1, in.read());
        }
    }
}

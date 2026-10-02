package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link ReaderInputStream} encodes UTF-8 text correctly when the
 * stream is consumed one byte at a time via {@link ReaderInputStream#read()}.
 */
public class ReaderInputStreamTest_testUTF8WithSingleByteRead {

    /** Charset name used to encode the reader's characters into stream bytes. */
    private static final String UTF_8 = StandardCharsets.UTF_8.name();

    /** Sample text containing multi-byte UTF-8 characters (accented letters). */
    private static final String TEST_STRING = "à peine arrivés nous entrâmes dans sa chambre";

    @Test
    void testUTF8WithSingleByteRead() throws IOException {
        // The bytes the stream is expected to produce, in order.
        final byte[] expectedBytes = TEST_STRING.getBytes(UTF_8);

        try (ReaderInputStream in = new ReaderInputStream(new StringReader(TEST_STRING), UTF_8)) {
            // Each single-byte read() must return the next encoded byte as an
            // unsigned value in the range [0, 255].
            for (final byte expectedByte : expectedBytes) {
                final int actualByte = in.read();
                assertTrue(actualByte >= 0, "read() should not signal end-of-stream early");
                assertTrue(actualByte <= 255, "read() must return an unsigned byte value");
                assertEquals(expectedByte, (byte) actualByte);
            }
            // After all bytes are consumed, the stream must report end-of-stream.
            assertEquals(-1, in.read(), "stream should be exhausted");
        }
    }
}

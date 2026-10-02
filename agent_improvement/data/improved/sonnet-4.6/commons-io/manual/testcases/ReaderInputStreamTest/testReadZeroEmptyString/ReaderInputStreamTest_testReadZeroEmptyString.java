package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.StringReader;
import org.junit.jupiter.api.Test;

/**
 * Tests that ReaderInputStream correctly handles zero-length read requests
 * and EOF detection on an empty underlying Reader.
 */
public class ReaderInputStreamTest_testReadZeroEmptyString {

    @SuppressWarnings("deprecation")
    @Test
    void testReadZeroEmptyString() throws Exception {
        try (ReaderInputStream inputStream = new ReaderInputStream(new StringReader(""))) {
            final byte[] buffer = new byte[30];

            // Reading zero bytes must always return 0, even when the stream is empty
            assertEquals(0, inputStream.read(buffer, 0, 0));

            // Reading one byte from an empty stream must return EOF (-1)
            assertEquals(-1, inputStream.read(buffer, 0, 1));

            // Zero-length reads must still return 0 even after EOF has been reached
            assertEquals(0, inputStream.read(buffer, 0, 0));

            // Subsequent non-zero reads must continue to return EOF
            assertEquals(-1, inputStream.read(buffer, 0, 1));
        }
    }
}

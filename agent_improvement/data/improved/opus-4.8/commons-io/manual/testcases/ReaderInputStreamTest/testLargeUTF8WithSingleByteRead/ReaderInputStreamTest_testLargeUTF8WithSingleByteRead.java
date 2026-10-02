package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link ReaderInputStream}, when read one byte at a time via {@link ReaderInputStream#read()},
 * reproduces exactly the UTF-8 encoded bytes of a large multibyte source string.
 */
public class ReaderInputStreamTest_testLargeUTF8WithSingleByteRead {

    /** A short phrase containing accented (multibyte in UTF-8) characters. */
    private static final String TEST_STRING = "à peine arrivés nous entrâmes dans sa chambre";

    /** A large source string built by repeating {@link #TEST_STRING}, exercising buffer refills. */
    private static final String LARGE_TEST_STRING = StringUtils.repeat(TEST_STRING, 100);

    @Test
    void testLargeUTF8WithSingleByteRead() throws IOException {
        final String charsetName = StandardCharsets.UTF_8.name();
        final byte[] expectedBytes = LARGE_TEST_STRING.getBytes(charsetName);

        try (ReaderInputStream in = new ReaderInputStream(new StringReader(LARGE_TEST_STRING), charsetName)) {
            // Each single-byte read must return the next encoded byte as an unsigned value in [0, 255].
            for (final byte expectedByte : expectedBytes) {
                final int actualByte = in.read();
                assertTrue(actualByte >= 0, "read() must not return a negative value before EOF");
                assertTrue(actualByte <= 255, "read() must return an unsigned byte value");
                assertEquals(expectedByte, (byte) actualByte);
            }
            // After all bytes are consumed, the stream must signal end-of-stream.
            assertEquals(-1, in.read(), "read() must return -1 at end of stream");
        }
    }
}

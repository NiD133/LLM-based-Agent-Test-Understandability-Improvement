package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.toLittleEndian;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link ByteUtils#toLittleEndian(java.io.OutputStream, long, int)}
 * writes a value to a stream as a little-endian byte sequence.
 */
public class ByteUtilsTest_testToLittleEndianToStream {

    @Test
    void testToLittleEndianToStream() throws IOException {
        // The value 0x040302, encoded little-endian over 3 bytes, is {2, 3, 4}:
        // byte 0 (least significant) = 2, byte 1 = 3, byte 2 = 4.
        final long value = 2 + 3 * 256 + 4 * 256 * 256;
        final int length = 3;
        final byte[] expectedLittleEndianBytes = { 2, 3, 4 };

        final byte[] writtenBytes;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            toLittleEndian(out, value, length);

            writtenBytes = out.toByteArray();
            assertArrayEquals(expectedLittleEndianBytes, writtenBytes);
        }

        // The bytes remain valid after the stream is closed.
        assertArrayEquals(expectedLittleEndianBytes, writtenBytes);
    }
}

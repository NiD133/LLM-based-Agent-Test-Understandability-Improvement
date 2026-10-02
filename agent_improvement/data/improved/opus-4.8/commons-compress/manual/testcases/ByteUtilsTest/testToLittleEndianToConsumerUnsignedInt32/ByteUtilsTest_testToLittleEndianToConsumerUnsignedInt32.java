package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.toLittleEndian;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.compress.utils.ByteUtils.OutputStreamByteConsumer;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link ByteUtils#toLittleEndian(ByteUtils.ByteConsumer, long, int)} writes a 32-bit
 * unsigned value to a {@link ByteUtils.ByteConsumer} as a little-endian byte sequence.
 */
public class ByteUtilsTest_testToLittleEndianToConsumerUnsignedInt32 {

    /** Number of bytes used to represent an unsigned 32-bit value. */
    private static final int UNSIGNED_INT32_BYTE_COUNT = 4;

    @Test
    void testToLittleEndianToConsumerUnsignedInt32() throws IOException {
        // A value whose most significant byte is 0x80 (128), so it exercises the
        // "unsigned" range that would be negative if interpreted as a signed int.
        // In little-endian order the four bytes are: 2, 3, 4, 128.
        final long value = 2 + 3 * 256 + 4 * 256 * 256 + 128L * 256 * 256 * 256;
        final byte[] expectedLittleEndianBytes = { 2, 3, 4, (byte) 128 };

        final byte[] writtenBytes;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            toLittleEndian(new OutputStreamByteConsumer(out), value, UNSIGNED_INT32_BYTE_COUNT);
            writtenBytes = out.toByteArray();
            assertArrayEquals(expectedLittleEndianBytes, writtenBytes);
        }

        // The bytes captured before closing the stream remain unchanged afterwards.
        assertArrayEquals(expectedLittleEndianBytes, writtenBytes);
    }
}

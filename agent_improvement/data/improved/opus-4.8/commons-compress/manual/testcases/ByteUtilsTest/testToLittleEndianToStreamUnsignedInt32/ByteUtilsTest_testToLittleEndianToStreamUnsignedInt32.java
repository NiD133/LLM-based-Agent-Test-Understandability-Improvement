package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.toLittleEndian;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link ByteUtils#toLittleEndian(java.io.OutputStream, long, int)}
 * writes a 32-bit unsigned value to a stream in little-endian byte order.
 */
public class ByteUtilsTest_testToLittleEndianToStreamUnsignedInt32 {

    @Test
    void testToLittleEndianToStreamUnsignedInt32() throws IOException {
        // The unsigned 32-bit value 0x80040302, whose most-significant byte (128)
        // sets the high bit. Each term contributes one byte, lowest first.
        final long unsignedInt32 =
                  2L                       // byte 0 (least significant)
                + 3L * 256                 // byte 1
                + 4L * 256 * 256           // byte 2
                + 128L * 256 * 256 * 256;  // byte 3 (most significant)

        // Little-endian: bytes are emitted least-significant first.
        final byte[] expectedLittleEndianBytes = { 2, 3, 4, (byte) 128 };

        final byte[] actualBytes;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            toLittleEndian(out, unsignedInt32, 4);
            actualBytes = out.toByteArray();
            assertArrayEquals(expectedLittleEndianBytes, actualBytes);
        }
        assertArrayEquals(expectedLittleEndianBytes, actualBytes);
    }
}

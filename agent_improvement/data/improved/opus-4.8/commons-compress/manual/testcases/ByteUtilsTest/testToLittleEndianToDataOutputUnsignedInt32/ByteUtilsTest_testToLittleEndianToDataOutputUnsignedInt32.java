package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.toLittleEndian;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link ByteUtils#toLittleEndian(DataOutput, long, int)} writes a 32-bit
 * unsigned value to a {@link DataOutput} as four little-endian bytes.
 */
public class ByteUtilsTest_testToLittleEndianToDataOutputUnsignedInt32 {

    /** Number of bytes used to represent an unsigned 32-bit value. */
    private static final int UINT32_BYTE_COUNT = 4;

    @Test
    void testToLittleEndianToDataOutputUnsignedInt32() throws IOException {
        // Bytes 0x02, 0x03, 0x04, 0x80 in little-endian order; the high byte (128)
        // makes the value exceed Integer.MAX_VALUE, so it must be handled as a long.
        final byte[] expectedLittleEndianBytes = { 2, 3, 4, (byte) 128 };
        final long value = 2 + 3 * 256 + 4 * 256 * 256 + 128L * 256 * 256 * 256;

        final byte[] writtenBytes;
        try (ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream()) {
            final DataOutput dataOut = new DataOutputStream(byteArrayOut);

            toLittleEndian(dataOut, value, UINT32_BYTE_COUNT);

            writtenBytes = byteArrayOut.toByteArray();
            assertArrayEquals(expectedLittleEndianBytes, writtenBytes);
        }
        assertArrayEquals(expectedLittleEndianBytes, writtenBytes);
    }
}

package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.toLittleEndian;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testToLittleEndianToByteArrayUnsignedInt32 {

    /**
     * Verifies that {@link ByteUtils#toLittleEndian(byte[], long, int, int)} writes a 32-bit
     * unsigned value into a byte array in little-endian order (least significant byte first).
     *
     * <p>The chosen value is composed so that each byte position holds a distinct, recognizable
     * number:</p>
     * <ul>
     *   <li>byte 0 (value &times; 256^0) = 2</li>
     *   <li>byte 1 (value &times; 256^1) = 3</li>
     *   <li>byte 2 (value &times; 256^2) = 4</li>
     *   <li>byte 3 (value &times; 256^3) = 128 &mdash; the high byte, which exercises the unsigned range</li>
     * </ul>
     */
    @Test
    void testToLittleEndianToByteArrayUnsignedInt32() {
        // Build the value 0x80040302 from its individual little-endian byte contributions.
        final long unsignedInt32Value = 2
                + 3 * 256
                + 4 * 256 * 256
                + 128L * 256 * 256 * 256;

        final byte[] actual = new byte[4];
        toLittleEndian(actual, unsignedInt32Value, 0, 4);

        // Bytes are ordered least significant first; 128 stays positive only when read as unsigned.
        final byte[] expectedLittleEndianBytes = { 2, 3, 4, (byte) 128 };
        assertArrayEquals(expectedLittleEndianBytes, actual);
    }
}

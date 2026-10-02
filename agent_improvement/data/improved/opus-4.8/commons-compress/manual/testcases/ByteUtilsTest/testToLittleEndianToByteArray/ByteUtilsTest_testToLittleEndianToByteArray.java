package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.toLittleEndian;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testToLittleEndianToByteArray {

    /**
     * Verifies that {@link ByteUtils#toLittleEndian(byte[], long, int, int)} writes a value
     * into a byte array in little-endian order, honouring the requested offset and length.
     */
    @Test
    void testToLittleEndianToByteArray() {
        // Value whose little-endian byte representation is { 2, 3, 4 }:
        //   byte 0 (1s)        -> 2
        //   byte 1 (256s)      -> 3
        //   byte 2 (65536s)    -> 4
        final long value = 2 + 3 * 256 + 4 * 256 * 256;

        // Destination buffer; index 0 is left untouched so we can confirm the offset is respected.
        final byte[] buffer = new byte[4];
        final int offset = 1;
        final int length = 3;

        toLittleEndian(buffer, value, offset, length);

        // Only the three bytes starting at the offset should have been written.
        final byte[] expected = { 2, 3, 4 };
        assertArrayEquals(expected, Arrays.copyOfRange(buffer, offset, offset + length));
    }
}

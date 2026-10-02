package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.toLittleEndian;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testToLittleEndianToByteArray {

    @Test
    void testToLittleEndianToByteArray() {
        // Little-endian value whose bytes are 2, 3, 4 (least-significant first)
        final long value = 2L | (3L << 8) | (4L << 16);
        final byte[] buffer = new byte[4];
        final int offset = 1;
        final int length = 3;

        toLittleEndian(buffer, value, offset, length);

        assertArrayEquals(new byte[] { 2, 3, 4 }, Arrays.copyOfRange(buffer, offset, offset + length));
    }
}

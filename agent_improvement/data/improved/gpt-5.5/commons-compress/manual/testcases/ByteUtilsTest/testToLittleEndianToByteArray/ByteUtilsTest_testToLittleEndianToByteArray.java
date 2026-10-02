package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.toLittleEndian;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testToLittleEndianToByteArray {

    @Test
    void testToLittleEndianToByteArray() {
        final byte[] littleEndianBytes = new byte[4];

        toLittleEndian(littleEndianBytes, 2 + 3 * 256 + 4 * 256 * 256, 1, 3);

        assertArrayEquals(new byte[] { 2, 3, 4 }, Arrays.copyOfRange(littleEndianBytes, 1, 4));
    }
}

package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.toLittleEndian;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testToLittleEndianToByteArrayUnsignedInt32 {

    @Test
    void testToLittleEndianToByteArrayUnsignedInt32() {
        final byte[] actualBytes = new byte[4];

        toLittleEndian(actualBytes, 2 + 3 * 256 + 4 * 256 * 256 + 128L * 256 * 256 * 256, 0, 4);

        final byte[] expectedLittleEndianBytes = { 2, 3, 4, (byte) 128 };
        assertArrayEquals(expectedLittleEndianBytes, actualBytes);
    }
}

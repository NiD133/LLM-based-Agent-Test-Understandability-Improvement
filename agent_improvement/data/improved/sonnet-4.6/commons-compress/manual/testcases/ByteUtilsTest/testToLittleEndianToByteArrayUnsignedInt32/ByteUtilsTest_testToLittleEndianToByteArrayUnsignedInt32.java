package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.toLittleEndian;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testToLittleEndianToByteArrayUnsignedInt32 {

    @Test
    void testToLittleEndianToByteArrayUnsignedInt32() {
        // 0x80040302 encodes bytes [0x02, 0x03, 0x04, 0x80] in little-endian order
        final long value = 0x80040302L;
        final byte[] buffer = new byte[4];

        toLittleEndian(buffer, value, 0, 4);

        assertArrayEquals(new byte[] { 2, 3, 4, (byte) 128 }, buffer);
    }
}

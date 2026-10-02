package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testFromLittleEndianFromArrayOneArgUnsignedInt32 {

    @Test
    void testFromLittleEndianFromArrayOneArgUnsignedInt32() {
        // Little-endian layout: least-significant byte first.
        // Bytes {0x02, 0x03, 0x04, 0x80} represent the 32-bit unsigned value 0x80040302L.
        final byte[] littleEndianBytes = { 2, 3, 4, (byte) 128 };

        // 0x80040302 = 128 * 2^24 + 4 * 2^16 + 3 * 2^8 + 2
        final long expected = 0x80040302L;

        assertEquals(expected, fromLittleEndian(littleEndianBytes));
    }
}

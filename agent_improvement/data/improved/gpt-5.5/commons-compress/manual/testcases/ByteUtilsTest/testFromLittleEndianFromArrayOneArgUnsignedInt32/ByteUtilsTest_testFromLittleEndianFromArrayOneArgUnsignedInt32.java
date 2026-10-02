package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testFromLittleEndianFromArrayOneArgUnsignedInt32 {

    private static final int BYTE_MULTIPLIER = 256;

    @Test
    void testFromLittleEndianFromArrayOneArgUnsignedInt32() {
        final byte leastSignificantByte = 2;
        final byte secondByte = 3;
        final byte thirdByte = 4;
        final byte mostSignificantByte = (byte) 128;
        final byte[] littleEndianBytes = {
            leastSignificantByte,
            secondByte,
            thirdByte,
            mostSignificantByte
        };

        final long expectedUnsignedInt32Value = leastSignificantByte
            + secondByte * BYTE_MULTIPLIER
            + thirdByte * BYTE_MULTIPLIER * BYTE_MULTIPLIER
            + 128L * BYTE_MULTIPLIER * BYTE_MULTIPLIER * BYTE_MULTIPLIER;

        assertEquals(expectedUnsignedInt32Value, fromLittleEndian(littleEndianBytes));
    }
}

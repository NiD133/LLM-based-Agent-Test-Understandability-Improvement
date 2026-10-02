package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testFromLittleEndianFromArrayOneArg {

    @Test
    void testFromLittleEndianFromArrayOneArg() {
        final byte leastSignificantByte = 2;
        final byte middleByte = 3;
        final byte mostSignificantByte = 4;
        final byte[] littleEndianBytes = { leastSignificantByte, middleByte, mostSignificantByte };

        assertEquals(2 + 3 * 256 + 4 * 256 * 256, fromLittleEndian(littleEndianBytes));
    }
}

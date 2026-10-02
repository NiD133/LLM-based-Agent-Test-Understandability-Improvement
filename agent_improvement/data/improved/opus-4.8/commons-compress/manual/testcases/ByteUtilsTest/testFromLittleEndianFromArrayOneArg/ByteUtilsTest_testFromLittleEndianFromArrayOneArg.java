package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testFromLittleEndianFromArrayOneArg {

    /**
     * {@link ByteUtils#fromLittleEndian(byte[])} should interpret the whole array as a
     * little-endian number: the first byte is the least significant, the last the most
     * significant. For the bytes {2, 3, 4} this means 2 + 3*256 + 4*65536.
     */
    @Test
    void testFromLittleEndianFromArrayOneArg() {
        final byte[] littleEndianBytes = { 2, 3, 4 };

        final long expected = 2 + 3 * 256 + 4 * 256 * 256;

        assertEquals(expected, fromLittleEndian(littleEndianBytes));
    }
}

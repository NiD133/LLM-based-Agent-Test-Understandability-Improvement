package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testFromLittleEndianFromArrayOneArg {

    @Test
    void testFromLittleEndianFromArrayOneArg() {
        // In little-endian order byte[0] is the least-significant byte,
        // so {2, 3, 4} represents 2 + 3*256 + 4*256*256.
        final byte[] leBytes = { 2, 3, 4 };
        final long expected = 2 + 3 * 256 + 4 * 256 * 256;
        assertEquals(expected, fromLittleEndian(leBytes));
    }
}

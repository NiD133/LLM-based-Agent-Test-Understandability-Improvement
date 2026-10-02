package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testFromLittleEndianFromArray {

    @Test
    void testFromLittleEndianFromArray() {
        // Bytes at offsets 1, 2, 3 are [2, 3, 4]; in little-endian order:
        // value = 2 * 256^0 + 3 * 256^1 + 4 * 256^2 = 0x040302
        final byte[] inputBytes = { 1, 2, 3, 4, 5 };
        final long expectedValue = 2 + 3 * 256 + 4 * 256 * 256;

        assertEquals(expectedValue, fromLittleEndian(inputBytes, 1, 3));
    }
}

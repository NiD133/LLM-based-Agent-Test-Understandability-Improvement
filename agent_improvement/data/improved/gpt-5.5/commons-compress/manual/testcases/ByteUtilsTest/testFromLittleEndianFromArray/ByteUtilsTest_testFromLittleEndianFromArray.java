package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testFromLittleEndianFromArray {

    @Test
    void testFromLittleEndianFromArray() {
        final byte[] bytes = { 1, 2, 3, 4, 5 };
        final long expectedValue = 2 + 3 * 256 + 4 * 256 * 256;

        assertEquals(expectedValue, fromLittleEndian(bytes, 1, 3));
    }
}

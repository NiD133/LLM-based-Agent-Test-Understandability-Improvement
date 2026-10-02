package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testFromLittleEndianFromArrayOneArgThrowsForLengthTooBig {

    @Test
    void testFromLittleEndianFromArrayOneArgThrowsForLengthTooBig() {
        final byte[] nineBytes = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

        assertThrows(IllegalArgumentException.class, () -> fromLittleEndian(nineBytes));
    }
}

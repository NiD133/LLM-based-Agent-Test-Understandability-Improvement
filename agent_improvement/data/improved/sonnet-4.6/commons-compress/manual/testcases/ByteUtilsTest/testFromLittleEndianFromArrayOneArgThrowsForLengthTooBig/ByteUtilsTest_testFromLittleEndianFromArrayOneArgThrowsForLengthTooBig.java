package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testFromLittleEndianFromArrayOneArgThrowsForLengthTooBig {

    // fromLittleEndian(byte[]) reads the array into a long, which is 8 bytes max.
    // Passing a 9-byte array must throw IllegalArgumentException.
    @Test
    void testFromLittleEndianFromArrayOneArgThrowsForLengthTooBig() {
        byte[] nineByteArray = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        assertThrows(IllegalArgumentException.class, () -> fromLittleEndian(nineByteArray));
    }
}

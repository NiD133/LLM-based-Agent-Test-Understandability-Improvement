package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testFromLittleEndianFromArrayThrowsForLengthTooBig {

    /**
     * {@link ByteUtils#fromLittleEndian(byte[], int, int)} reads up to eight bytes into a
     * long. Asking for more than eight bytes is invalid and must be rejected with an
     * {@link IllegalArgumentException}, regardless of the array's contents.
     */
    @Test
    void testFromLittleEndianFromArrayThrowsForLengthTooBig() {
        final byte[] source = ArrayUtils.EMPTY_BYTE_ARRAY;
        final int offset = 0;
        final int tooManyBytes = 9; // exceeds the maximum of eight bytes for a long

        assertThrows(IllegalArgumentException.class,
            () -> fromLittleEndian(source, offset, tooManyBytes));
    }
}

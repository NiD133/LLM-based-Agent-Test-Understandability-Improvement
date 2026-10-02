package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testFromLittleEndianFromArrayThrowsForLengthTooBig {

    private static final int ARRAY_START_OFFSET = 0;
    private static final int TOO_MANY_BYTES_FOR_LONG = Long.BYTES + 1;

    @Test
    void testFromLittleEndianFromArrayThrowsForLengthTooBig() {
        assertThrows(IllegalArgumentException.class,
                () -> fromLittleEndian(ArrayUtils.EMPTY_BYTE_ARRAY, ARRAY_START_OFFSET, TOO_MANY_BYTES_FOR_LONG));
    }
}

package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testFromLittleEndianFromStreamThrowsForLengthTooBig {

    private static final int TOO_MANY_BYTES_FOR_LONG = 9;

    @Test
    @SuppressWarnings("deprecation")
    void testFromLittleEndianFromStreamThrowsForLengthTooBig() {
        assertThrows(IllegalArgumentException.class,
                () -> fromLittleEndian(new ByteArrayInputStream(ArrayUtils.EMPTY_BYTE_ARRAY), TOO_MANY_BYTES_FOR_LONG));
    }
}

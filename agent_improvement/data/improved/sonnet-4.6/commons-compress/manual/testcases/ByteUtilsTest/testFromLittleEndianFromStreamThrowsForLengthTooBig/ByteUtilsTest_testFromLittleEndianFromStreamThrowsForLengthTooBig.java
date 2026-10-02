package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testFromLittleEndianFromStreamThrowsForLengthTooBig {

    // A long is 8 bytes; requesting 9 bytes exceeds the maximum supported length.
    private static final int LENGTH_EXCEEDING_MAXIMUM = 9;

    @Test
    void testFromLittleEndianFromStreamThrowsForLengthTooBig() {
        ByteArrayInputStream emptyStream = new ByteArrayInputStream(ArrayUtils.EMPTY_BYTE_ARRAY);
        assertThrows(IllegalArgumentException.class,
                () -> fromLittleEndian(emptyStream, LENGTH_EXCEEDING_MAXIMUM));
    }
}

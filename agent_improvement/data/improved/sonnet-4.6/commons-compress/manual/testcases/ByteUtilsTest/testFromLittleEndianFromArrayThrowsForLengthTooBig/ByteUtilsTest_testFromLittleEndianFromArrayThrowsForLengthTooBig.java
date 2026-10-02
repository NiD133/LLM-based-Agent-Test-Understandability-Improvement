package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testFromLittleEndianFromArrayThrowsForLengthTooBig {

    // The maximum number of bytes that can be read into a long is 8.
    // Requesting 9 bytes must trigger an IllegalArgumentException.
    private static final int OFFSET_ZERO = 0;
    private static final int LENGTH_EXCEEDING_MAX = 9;

    @Test
    void testFromLittleEndianFromArrayThrowsForLengthTooBig() {
        assertThrows(
            IllegalArgumentException.class,
            () -> fromLittleEndian(ArrayUtils.EMPTY_BYTE_ARRAY, OFFSET_ZERO, LENGTH_EXCEEDING_MAX)
        );
    }
}

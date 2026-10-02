package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link ByteUtils#fromLittleEndian(InputStream, int)} rejects a
 * requested length that exceeds the eight bytes a {@code long} can hold.
 */
public class ByteUtilsTest_testFromLittleEndianFromStreamThrowsForLengthTooBig {

    /** A {@code long} is 8 bytes wide, so reading more than 8 bytes is invalid. */
    private static final int MAX_SUPPORTED_LENGTH = 8;

    @Test
    void rejectsLengthLargerThanEightBytes() {
        final int tooManyBytes = MAX_SUPPORTED_LENGTH + 1;
        final InputStream emptyStream = new ByteArrayInputStream(ArrayUtils.EMPTY_BYTE_ARRAY);

        assertThrows(IllegalArgumentException.class,
                () -> fromLittleEndian(emptyStream, tooManyBytes));
    }
}

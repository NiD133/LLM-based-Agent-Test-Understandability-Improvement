package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link ByteUtils#fromLittleEndian(byte[])} rejects arrays that are too long.
 *
 * <p>A {@code long} can hold at most eight bytes, so passing a byte array with more than eight
 * elements must be rejected with an {@link IllegalArgumentException}.</p>
 */
public class ByteUtilsTest_testFromLittleEndianFromArrayOneArgThrowsForLengthTooBig {

    /** Nine bytes — one more than a {@code long} can represent. */
    private static final byte[] NINE_BYTES = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

    @Test
    void fromLittleEndianRejectsArrayLongerThanEightBytes() {
        assertThrows(IllegalArgumentException.class, () -> fromLittleEndian(NINE_BYTES));
    }
}

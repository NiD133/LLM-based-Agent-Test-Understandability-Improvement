package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link ByteUtils#fromLittleEndian(byte[])} rejects arrays that
 * are too large to fit into a {@code long}.
 */
public class ByteUtilsTest_testFromLittleEndianFromArrayOneArgThrowsForLengthTooBig {

    /**
     * A {@code long} holds at most eight bytes, so decoding a nine-byte array
     * must fail with an {@link IllegalArgumentException}.
     */
    @Test
    void testFromLittleEndianFromArrayOneArgThrowsForLengthTooBig() {
        final byte[] nineBytes = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

        assertThrows(IllegalArgumentException.class, () -> fromLittleEndian(nineBytes));
    }
}

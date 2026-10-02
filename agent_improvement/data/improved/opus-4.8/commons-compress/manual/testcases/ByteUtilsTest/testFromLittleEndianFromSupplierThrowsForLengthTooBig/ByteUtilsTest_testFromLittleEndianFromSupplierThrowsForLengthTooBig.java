package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;

import org.apache.commons.compress.utils.ByteUtils.InputStreamByteSupplier;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link ByteUtils#fromLittleEndian(ByteUtils.ByteSupplier, int)}
 * rejects a requested length that exceeds the maximum a long can hold.
 *
 * <p>A long is eight bytes wide, so any length greater than 8 must be refused
 * with an {@link IllegalArgumentException}, regardless of how many bytes the
 * supplier can actually provide.</p>
 */
public class ByteUtilsTest_testFromLittleEndianFromSupplierThrowsForLengthTooBig {

    /** Larger than the eight bytes that fit into a long, so it must be rejected. */
    private static final int LENGTH_EXCEEDING_LONG_WIDTH = 9;

    @Test
    void testFromLittleEndianFromSupplierThrowsForLengthTooBig() {
        final InputStreamByteSupplier emptySupplier =
                new InputStreamByteSupplier(new ByteArrayInputStream(ArrayUtils.EMPTY_BYTE_ARRAY));

        assertThrows(IllegalArgumentException.class,
                () -> fromLittleEndian(emptySupplier, LENGTH_EXCEEDING_LONG_WIDTH));
    }
}

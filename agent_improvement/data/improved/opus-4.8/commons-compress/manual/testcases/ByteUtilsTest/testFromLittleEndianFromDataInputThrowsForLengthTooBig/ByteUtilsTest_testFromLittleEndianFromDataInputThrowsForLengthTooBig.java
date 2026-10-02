package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testFromLittleEndianFromDataInputThrowsForLengthTooBig {

    /**
     * {@code fromLittleEndian} can read at most eight bytes into a long, so asking
     * it to read more must be rejected with an {@link IllegalArgumentException}
     * regardless of how much data the {@link DataInput} actually holds.
     */
    @Test
    void testFromLittleEndianFromDataInputThrowsForLengthTooBig() {
        // A long holds 8 bytes; requesting 9 exceeds that limit.
        final int lengthLargerThanLong = 9;
        final DataInput input = new DataInputStream(new ByteArrayInputStream(ArrayUtils.EMPTY_BYTE_ARRAY));

        assertThrows(IllegalArgumentException.class,
                () -> fromLittleEndian(input, lengthLargerThanLong));
    }
}

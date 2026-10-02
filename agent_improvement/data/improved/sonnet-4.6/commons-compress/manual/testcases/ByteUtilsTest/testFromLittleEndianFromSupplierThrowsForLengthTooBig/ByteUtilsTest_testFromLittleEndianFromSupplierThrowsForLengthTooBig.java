package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;

import org.apache.commons.compress.utils.ByteUtils.InputStreamByteSupplier;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testFromLittleEndianFromSupplierThrowsForLengthTooBig {

    // A long is 8 bytes; requesting 9 bytes exceeds the maximum allowed length.
    private static final int LENGTH_EXCEEDING_MAX = 9;

    @Test
    void testFromLittleEndianFromSupplierThrowsForLengthTooBig() {
        ByteArrayInputStream emptyStream = new ByteArrayInputStream(ArrayUtils.EMPTY_BYTE_ARRAY);
        InputStreamByteSupplier supplier = new InputStreamByteSupplier(emptyStream);

        assertThrows(IllegalArgumentException.class,
                () -> fromLittleEndian(supplier, LENGTH_EXCEEDING_MAX));
    }
}

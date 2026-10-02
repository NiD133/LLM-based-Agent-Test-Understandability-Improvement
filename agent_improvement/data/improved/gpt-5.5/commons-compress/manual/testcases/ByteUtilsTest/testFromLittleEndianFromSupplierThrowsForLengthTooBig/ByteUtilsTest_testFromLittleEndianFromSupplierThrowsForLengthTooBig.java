package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;

import org.apache.commons.compress.utils.ByteUtils.InputStreamByteSupplier;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testFromLittleEndianFromSupplierThrowsForLengthTooBig {

    private static final int TOO_MANY_BYTES_FOR_LONG = 9;

    @Test
    void testFromLittleEndianFromSupplierThrowsForLengthTooBig() {
        final InputStreamByteSupplier emptyInputSupplier = new InputStreamByteSupplier(new ByteArrayInputStream(ArrayUtils.EMPTY_BYTE_ARRAY));

        assertThrows(IllegalArgumentException.class, () -> fromLittleEndian(emptyInputSupplier, TOO_MANY_BYTES_FOR_LONG));
    }
}

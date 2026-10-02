package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testFromLittleEndianFromDataInputThrowsForLengthTooBig {

    private static final int TOO_MANY_BYTES_FOR_LONG = 9;

    @Test
    void testFromLittleEndianFromDataInputThrowsForLengthTooBig() {
        final DataInput input = new DataInputStream(new ByteArrayInputStream(ArrayUtils.EMPTY_BYTE_ARRAY));

        assertThrows(IllegalArgumentException.class, () -> fromLittleEndian(input, TOO_MANY_BYTES_FOR_LONG));
    }
}

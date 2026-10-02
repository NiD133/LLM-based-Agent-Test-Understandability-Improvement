package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testFromLittleEndianFromDataInputThrowsForLengthTooBig {

    // fromLittleEndian reads into a long (max 8 bytes); 9 exceeds the supported limit
    private static final int EXCEEDS_MAX_LONG_BYTE_LENGTH = 9;

    @Test
    void testFromLittleEndianFromDataInputThrowsForLengthTooBig() {
        final DataInput din = new DataInputStream(new ByteArrayInputStream(ArrayUtils.EMPTY_BYTE_ARRAY));
        assertThrows(IllegalArgumentException.class,
                () -> fromLittleEndian(din, EXCEEDS_MAX_LONG_BYTE_LENGTH));
    }
}

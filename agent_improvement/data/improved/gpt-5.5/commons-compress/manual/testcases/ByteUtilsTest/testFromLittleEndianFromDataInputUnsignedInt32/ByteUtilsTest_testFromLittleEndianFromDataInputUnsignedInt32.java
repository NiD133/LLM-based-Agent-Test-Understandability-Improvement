package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.fromLittleEndian;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testFromLittleEndianFromDataInputUnsignedInt32 {

    private static final byte[] UNSIGNED_INT32_LITTLE_ENDIAN_BYTES = { 2, 3, 4, (byte) 128 };
    private static final long EXPECTED_UNSIGNED_INT32_VALUE = 2 + 3 * 256 + 4 * 256 * 256 + 128L * 256 * 256 * 256;

    @Test
    void testFromLittleEndianFromDataInputUnsignedInt32() throws IOException {
        final DataInput dataInput = new DataInputStream(new ByteArrayInputStream(UNSIGNED_INT32_LITTLE_ENDIAN_BYTES));

        assertEquals(EXPECTED_UNSIGNED_INT32_VALUE, fromLittleEndian(dataInput, 4));
    }
}

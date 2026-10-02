package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.toLittleEndian;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testToLittleEndianToDataOutputUnsignedInt32 {

    // Unsigned 32-bit value whose little-endian byte representation is [2, 3, 4, 128]
    private static final long UINT32_VALUE = 2 + 3 * 256 + 4 * 256 * 256 + 128L * 256 * 256 * 256;
    private static final byte[] EXPECTED_BYTES = { 2, 3, 4, (byte) 128 };
    private static final int BYTES_PER_32BIT_INT = 4;

    @Test
    void testToLittleEndianToDataOutputUnsignedInt32() throws IOException {
        final byte[] byteArray;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            final DataOutput dos = new DataOutputStream(bos);
            toLittleEndian(dos, UINT32_VALUE, BYTES_PER_32BIT_INT);
            byteArray = bos.toByteArray();
            assertArrayEquals(EXPECTED_BYTES, byteArray);
        }
        assertArrayEquals(EXPECTED_BYTES, byteArray);
    }
}

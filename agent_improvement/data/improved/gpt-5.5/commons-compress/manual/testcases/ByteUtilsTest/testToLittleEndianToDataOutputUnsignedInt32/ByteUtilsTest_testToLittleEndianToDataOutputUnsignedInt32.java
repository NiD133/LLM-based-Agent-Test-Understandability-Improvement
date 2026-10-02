package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.toLittleEndian;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testToLittleEndianToDataOutputUnsignedInt32 {

    private static final int BYTES_IN_UNSIGNED_INT_32 = 4;
    private static final long VALUE_WITH_HIGH_BIT_SET = 2 + 3 * 256 + 4 * 256 * 256 + 128L * 256 * 256 * 256;
    private static final byte[] LITTLE_ENDIAN_BYTES = { 2, 3, 4, (byte) 128 };

    @Test
    void testToLittleEndianToDataOutputUnsignedInt32() throws IOException {
        final byte[] actualBytes;

        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            final DataOutput dataOutput = new DataOutputStream(output);

            toLittleEndian(dataOutput, VALUE_WITH_HIGH_BIT_SET, BYTES_IN_UNSIGNED_INT_32);

            actualBytes = output.toByteArray();
            assertArrayEquals(LITTLE_ENDIAN_BYTES, actualBytes);
        }

        assertArrayEquals(LITTLE_ENDIAN_BYTES, actualBytes);
    }
}

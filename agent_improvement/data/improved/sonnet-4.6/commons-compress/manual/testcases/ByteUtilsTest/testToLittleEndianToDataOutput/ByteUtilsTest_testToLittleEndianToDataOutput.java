package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.toLittleEndian;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testToLittleEndianToDataOutput {

    // 0x040302 == 2 + 3*256 + 4*256*256: in little-endian order the bytes are [0x02, 0x03, 0x04]
    private static final long THREE_BYTE_VALUE = 0x040302L;
    private static final int BYTE_COUNT = 3;
    private static final byte[] EXPECTED_LITTLE_ENDIAN_BYTES = { 2, 3, 4 };

    @Test
    void testToLittleEndianToDataOutput() throws IOException {
        final byte[] actualBytes;
        try (ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
            final DataOutput dataOutput = new DataOutputStream(buffer);
            toLittleEndian(dataOutput, THREE_BYTE_VALUE, BYTE_COUNT);
            actualBytes = buffer.toByteArray();
            assertArrayEquals(EXPECTED_LITTLE_ENDIAN_BYTES, actualBytes);
        }
        // Confirm the byte array reference is still valid after the stream closes
        assertArrayEquals(EXPECTED_LITTLE_ENDIAN_BYTES, actualBytes);
    }
}

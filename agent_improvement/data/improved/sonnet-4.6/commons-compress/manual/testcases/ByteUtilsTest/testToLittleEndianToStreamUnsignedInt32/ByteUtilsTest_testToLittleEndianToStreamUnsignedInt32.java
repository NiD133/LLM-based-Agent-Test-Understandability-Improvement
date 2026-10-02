package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.toLittleEndian;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testToLittleEndianToStreamUnsignedInt32 {

    // Little-endian encoding of the 4-byte sequence {2, 3, 4, 128}:
    // byte0=2, byte1=3*256, byte2=4*256^2, byte3=128*256^3
    private static final long LITTLE_ENDIAN_2_3_4_128 =
            2 + 3 * 256 + 4 * 256 * 256 + 128L * 256 * 256 * 256;

    @Test
    void testToLittleEndianToStreamUnsignedInt32() throws IOException {
        final byte[] expectedBytes = { 2, 3, 4, (byte) 128 };
        final byte[] actualBytes;

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            toLittleEndian(outputStream, LITTLE_ENDIAN_2_3_4_128, 4);
            actualBytes = outputStream.toByteArray();
            assertArrayEquals(expectedBytes, actualBytes);
        }

        assertArrayEquals(expectedBytes, actualBytes);
    }
}

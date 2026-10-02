package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.toLittleEndian;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testToLittleEndianToStreamUnsignedInt32 {

    @Test
    void testToLittleEndianToStreamUnsignedInt32() throws IOException {
        final byte[] expectedLittleEndianBytes = { 2, 3, 4, (byte) 128 };
        final long unsignedInt32Value = 2 + 3 * 256 + 4 * 256 * 256 + 128L * 256 * 256 * 256;

        final byte[] actualLittleEndianBytes;
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            toLittleEndian(output, unsignedInt32Value, 4);
            actualLittleEndianBytes = output.toByteArray();
            assertArrayEquals(expectedLittleEndianBytes, actualLittleEndianBytes);
        }

        assertArrayEquals(expectedLittleEndianBytes, actualLittleEndianBytes);
    }
}

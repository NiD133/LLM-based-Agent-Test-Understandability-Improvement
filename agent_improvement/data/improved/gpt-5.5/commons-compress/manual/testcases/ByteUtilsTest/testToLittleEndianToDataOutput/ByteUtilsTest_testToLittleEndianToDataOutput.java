package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.toLittleEndian;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testToLittleEndianToDataOutput {

    @Test
    void testToLittleEndianToDataOutput() throws IOException {
        final byte[] expectedLittleEndianBytes = { 2, 3, 4 };
        final byte[] actualLittleEndianBytes;

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            final DataOutput dataOutput = new DataOutputStream(outputStream);

            toLittleEndian(dataOutput, 2 + 3 * 256 + 4 * 256 * 256, 3);

            actualLittleEndianBytes = outputStream.toByteArray();
            assertArrayEquals(expectedLittleEndianBytes, actualLittleEndianBytes);
        }

        assertArrayEquals(expectedLittleEndianBytes, actualLittleEndianBytes);
    }
}

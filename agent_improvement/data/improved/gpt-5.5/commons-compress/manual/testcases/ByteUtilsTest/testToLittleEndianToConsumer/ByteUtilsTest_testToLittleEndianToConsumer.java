package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.toLittleEndian;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.compress.utils.ByteUtils.OutputStreamByteConsumer;
import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testToLittleEndianToConsumer {

    @Test
    void testToLittleEndianToConsumer() throws IOException {
        final byte[] expectedLittleEndianBytes = { 2, 3, 4 };
        final byte[] actualLittleEndianBytes;

        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            toLittleEndian(new OutputStreamByteConsumer(output), 2 + 3 * 256 + 4 * 256 * 256, 3);
            actualLittleEndianBytes = output.toByteArray();
            assertArrayEquals(expectedLittleEndianBytes, actualLittleEndianBytes);
        }

        assertArrayEquals(expectedLittleEndianBytes, actualLittleEndianBytes);
    }
}

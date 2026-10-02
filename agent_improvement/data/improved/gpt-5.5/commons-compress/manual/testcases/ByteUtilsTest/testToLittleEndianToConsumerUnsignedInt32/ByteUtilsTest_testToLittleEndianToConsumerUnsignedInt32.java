package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.toLittleEndian;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.compress.utils.ByteUtils.OutputStreamByteConsumer;
import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testToLittleEndianToConsumerUnsignedInt32 {

    @Test
    void testToLittleEndianToConsumerUnsignedInt32() throws IOException {
        final byte[] byteArray;
        final byte[] expected = { 2, 3, 4, (byte) 128 };

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            toLittleEndian(new OutputStreamByteConsumer(bos), 2 + 3 * 256 + 4 * 256 * 256 + 128L * 256 * 256 * 256, 4);
            byteArray = bos.toByteArray();

            assertArrayEquals(expected, byteArray);
        }

        assertArrayEquals(expected, byteArray);
    }
}

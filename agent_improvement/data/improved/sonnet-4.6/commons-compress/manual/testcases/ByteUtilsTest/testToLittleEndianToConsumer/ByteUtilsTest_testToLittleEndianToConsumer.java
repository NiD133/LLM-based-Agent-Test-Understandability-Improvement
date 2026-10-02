package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.toLittleEndian;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.compress.utils.ByteUtils.OutputStreamByteConsumer;
import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testToLittleEndianToConsumer {

    // 262914 encoded as 3 little-endian bytes: least-significant byte first → [2, 3, 4]
    private static final long VALUE_2_PLUS_3x256_PLUS_4x256x256 = 2 + 3 * 256 + 4 * 256 * 256;
    private static final int THREE_BYTES = 3;

    @Test
    void testToLittleEndianToConsumer() throws IOException {
        final byte[] expected = { 2, 3, 4 };

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            toLittleEndian(new OutputStreamByteConsumer(bos), VALUE_2_PLUS_3x256_PLUS_4x256x256, THREE_BYTES);
            assertArrayEquals(expected, bos.toByteArray());
        }
    }
}

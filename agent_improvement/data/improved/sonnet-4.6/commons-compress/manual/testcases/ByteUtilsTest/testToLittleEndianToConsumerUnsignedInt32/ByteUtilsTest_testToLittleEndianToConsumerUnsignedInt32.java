package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.toLittleEndian;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.compress.utils.ByteUtils.OutputStreamByteConsumer;
import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testToLittleEndianToConsumerUnsignedInt32 {

    // 0x80040302 in little-endian byte order is [0x02, 0x03, 0x04, 0x80]
    private static final long VALUE_0x80040302 = 0x80040302L;
    private static final byte[] EXPECTED_LE_BYTES = { 2, 3, 4, (byte) 128 };
    private static final int UINT32_BYTE_LENGTH = 4;

    @Test
    void testToLittleEndianToConsumerUnsignedInt32() throws IOException {
        final byte[] byteArray;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            toLittleEndian(new OutputStreamByteConsumer(bos), VALUE_0x80040302, UINT32_BYTE_LENGTH);
            byteArray = bos.toByteArray();
            assertArrayEquals(EXPECTED_LE_BYTES, byteArray);
        }
        assertArrayEquals(EXPECTED_LE_BYTES, byteArray);
    }
}

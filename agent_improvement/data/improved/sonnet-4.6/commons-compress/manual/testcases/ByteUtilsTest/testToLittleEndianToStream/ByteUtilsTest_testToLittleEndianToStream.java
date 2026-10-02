package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.toLittleEndian;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testToLittleEndianToStream {

    // 0x040302 encodes the 3-byte little-endian sequence [2, 3, 4]:
    // byte 0 = 0x02, byte 1 = 0x03, byte 2 = 0x04
    private static final long THREE_BYTE_LITTLE_ENDIAN_VALUE = 0x040302L;

    @Test
    void testToLittleEndianToStream() throws IOException {
        final byte[] expected = { 2, 3, 4 };
        final byte[] actual;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            toLittleEndian(outputStream, THREE_BYTE_LITTLE_ENDIAN_VALUE, 3);
            actual = outputStream.toByteArray();
            assertArrayEquals(expected, actual);
        }
        assertArrayEquals(expected, actual);
    }
}

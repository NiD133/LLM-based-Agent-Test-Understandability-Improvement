package org.apache.commons.compress.utils;

import static org.apache.commons.compress.utils.ByteUtils.toLittleEndian;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.compress.utils.ByteUtils.OutputStreamByteConsumer;
import org.junit.jupiter.api.Test;

public class ByteUtilsTest_testToLittleEndianToConsumer {

    /**
     * Verifies that {@link ByteUtils#toLittleEndian(ByteUtils.ByteConsumer, long, int)}
     * writes a value to a consumer as a little-endian byte sequence.
     *
     * <p>The value 0x040302 is encoded over 3 bytes. In little-endian order the
     * least-significant byte comes first, so the expected output is {2, 3, 4}:</p>
     * <ul>
     *   <li>byte 0 (value &amp; 0xff)        = 2</li>
     *   <li>byte 1 ((value &gt;&gt; 8) &amp; 0xff)  = 3</li>
     *   <li>byte 2 ((value &gt;&gt; 16) &amp; 0xff) = 4</li>
     * </ul>
     */
    @Test
    void testToLittleEndianToConsumer() throws IOException {
        final int byteCount = 3;
        // 2 + 3*256 + 4*65536: the byte values 2, 3, 4 packed in little-endian order.
        final long value = 2 + 3 * 256 + 4 * 256 * 256;
        final byte[] expectedLittleEndianBytes = { 2, 3, 4 };

        final byte[] writtenBytes;
        try (ByteArrayOutputStream collectedBytes = new ByteArrayOutputStream()) {
            toLittleEndian(new OutputStreamByteConsumer(collectedBytes), value, byteCount);

            writtenBytes = collectedBytes.toByteArray();
            assertArrayEquals(expectedLittleEndianBytes, writtenBytes);
        }

        // The bytes remain available after the stream is closed.
        assertArrayEquals(expectedLittleEndianBytes, writtenBytes);
    }
}

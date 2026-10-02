package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

public class HexTest_testEncodeHexReadOnlyByteBuffer {

    /**
     * Verifies that Hex.encodeHex correctly encodes a read-only ByteBuffer.
     *
     * Regression test for CODEC-261: read-only buffers must be supported without
     * attempting to access the backing array, which would throw ReadOnlyBufferException.
     */
    @Test
    void testEncodeHexReadOnlyByteBuffer() {
        // byte value 10 (decimal) = 0x0A, so the expected hex encoding is "0a"
        final byte inputByte = 10;
        final ByteBuffer readOnlyBuffer = ByteBuffer.wrap(new byte[] { inputByte }).asReadOnlyBuffer();

        final char[] hexChars = Hex.encodeHex(readOnlyBuffer);

        assertEquals("0a", String.valueOf(hexChars));
    }
}

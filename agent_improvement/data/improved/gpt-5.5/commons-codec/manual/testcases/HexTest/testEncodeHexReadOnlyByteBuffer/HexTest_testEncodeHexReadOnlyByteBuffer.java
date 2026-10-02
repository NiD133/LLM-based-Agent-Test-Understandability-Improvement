package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

public class HexTest_testEncodeHexReadOnlyByteBuffer {

    /**
     * Test encoding of a read only byte buffer.
     * See CODEC-261.
     */
    @Test
    void testEncodeHexReadOnlyByteBuffer() {
        final char[] chars = Hex.encodeHex(ByteBuffer.wrap(new byte[] { 10 }).asReadOnlyBuffer());
        assertEquals("0a", String.valueOf(chars));
    }
}

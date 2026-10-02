package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Hex#encodeHex(ByteBuffer)} when the source buffer is read-only.
 */
public class HexTest_testEncodeHexReadOnlyByteBuffer {

    /**
     * A read-only {@link ByteBuffer} must encode to hex exactly like a normal
     * buffer: the single byte {@code 10} (0x0A) should become the string "0a".
     *
     * <p>Regression test for CODEC-261.</p>
     */
    @Test
    void testEncodeHexReadOnlyByteBuffer() {
        final ByteBuffer readOnlyBuffer = ByteBuffer.wrap(new byte[] { 10 }).asReadOnlyBuffer();

        final char[] encodedHex = Hex.encodeHex(readOnlyBuffer);

        assertEquals("0a", String.valueOf(encodedHex));
    }
}

package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.nio.ByteBuffer;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link Hex#decode(ByteBuffer)} with an empty buffer.
 */
public class HexTest_testDecodeByteBufferEmpty {

    /**
     * Decoding an empty {@link ByteBuffer} should yield an empty byte array,
     * since two hex characters are required to produce a single byte.
     */
    @Test
    void testDecodeByteBufferEmpty() throws DecoderException {
        final ByteBuffer emptyBuffer = ByteBuffer.allocate(0);

        final byte[] decoded = new Hex().decode(emptyBuffer);

        assertArrayEquals(new byte[0], decoded);
    }
}

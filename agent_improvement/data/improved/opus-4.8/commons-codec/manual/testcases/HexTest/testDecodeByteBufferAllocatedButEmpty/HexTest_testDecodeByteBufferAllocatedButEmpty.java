package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

/**
 * Verifies how {@link Hex#decode(ByteBuffer)} behaves when given a buffer that
 * has capacity but no readable bytes (i.e. {@code remaining() == 0}).
 */
public class HexTest_testDecodeByteBufferAllocatedButEmpty {

    /**
     * Decoding a buffer with nothing left to read should yield an empty byte
     * array and should not change the buffer's already-zero remaining count.
     */
    @Test
    void testDecodeByteBufferAllocatedButEmpty() throws DecoderException {
        // Allocate a buffer with spare capacity, then flip it without writing
        // anything so that no bytes remain to be read.
        final ByteBuffer emptyBuffer = ByteBuffer.allocate(10);
        emptyBuffer.flip();

        final byte[] decoded = new Hex().decode(emptyBuffer);

        assertArrayEquals(new byte[0], decoded);
        assertEquals(0, emptyBuffer.remaining());
    }
}

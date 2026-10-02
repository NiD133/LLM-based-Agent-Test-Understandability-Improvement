package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testDecodeByteBufferAllocatedButEmpty {

    /**
     * Allocates a heap ByteBuffer with the given capacity.
     * Subclasses may override this to use direct allocation instead.
     */
    protected ByteBuffer allocate(final int capacity) {
        return ByteBuffer.allocate(capacity);
    }

    /**
     * Verifies that decoding an allocated-but-empty ByteBuffer yields an empty byte array
     * and leaves zero bytes remaining in the buffer.
     *
     * <p>A ByteBuffer is "allocated but empty" when it has been allocated with a non-zero
     * capacity but then immediately flipped (position reset to 0, limit = 0), so that
     * {@link ByteBuffer#remaining()} returns 0. The Hex decoder should treat this the
     * same as an empty input and return a zero-length byte array.
     */
    @Test
    void testDecodeByteBufferAllocatedButEmpty() throws DecoderException {
        // Allocate a buffer with capacity, then flip so remaining() == 0
        final ByteBuffer emptyBuffer = allocate(10);
        emptyBuffer.flip();

        assertArrayEquals(new byte[0], new Hex().decode(emptyBuffer));
        assertEquals(0, emptyBuffer.remaining());
    }
}

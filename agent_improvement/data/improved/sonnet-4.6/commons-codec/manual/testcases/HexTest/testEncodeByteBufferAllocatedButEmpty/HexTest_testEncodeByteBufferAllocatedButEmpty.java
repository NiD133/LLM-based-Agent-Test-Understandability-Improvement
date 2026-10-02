package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

public class HexTest_testEncodeByteBufferAllocatedButEmpty {

    protected ByteBuffer allocate(final int capacity) {
        return ByteBuffer.allocate(capacity);
    }

    @Test
    void testEncodeByteBufferAllocatedButEmpty() {
        // Allocate a buffer with capacity but write nothing, so remaining() == 0
        final ByteBuffer emptyBuffer = allocate(10);
        emptyBuffer.flip();

        final byte[] encoded = new Hex().encode(emptyBuffer);

        assertArrayEquals(new byte[0], encoded);
        assertEquals(0, emptyBuffer.remaining());
    }
}

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
        final ByteBuffer emptyBufferWithCapacity = allocate(10);
        emptyBufferWithCapacity.flip();

        assertArrayEquals(new byte[0], new Hex().encode(emptyBufferWithCapacity));
        assertEquals(0, emptyBufferWithCapacity.remaining());
    }
}

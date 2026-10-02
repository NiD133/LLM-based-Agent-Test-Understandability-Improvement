package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

public class HexTest_testEncodeByteBufferEmpty {

    /**
     * Keeps buffer creation consistent with the original Hex test fixture.
     *
     * @param capacity the buffer capacity
     * @return a newly allocated byte buffer
     */
    protected ByteBuffer allocate(final int capacity) {
        return ByteBuffer.allocate(capacity);
    }

    @Test
    void testEncodeByteBufferEmpty() {
        assertArrayEquals(new byte[0], new Hex().encode(allocate(0)));
    }
}

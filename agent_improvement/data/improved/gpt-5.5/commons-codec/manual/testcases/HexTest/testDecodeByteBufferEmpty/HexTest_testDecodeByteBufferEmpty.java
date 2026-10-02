package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.nio.ByteBuffer;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testDecodeByteBufferEmpty {

    /**
     * Allocates the buffer used by the decode tests.
     *
     * @param capacity the buffer capacity
     * @return a heap byte buffer with the requested capacity
     */
    protected ByteBuffer allocate(final int capacity) {
        return ByteBuffer.allocate(capacity);
    }

    @Test
    void testDecodeByteBufferEmpty() throws DecoderException {
        assertArrayEquals(new byte[0], new Hex().decode(allocate(0)));
    }
}

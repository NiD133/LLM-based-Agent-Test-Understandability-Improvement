package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.nio.ByteBuffer;

import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testEncodeByteBufferObjectEmpty {

    /**
     * Allocates the buffer used by the test.
     *
     * <p>This mirrors the original test fixture method so the encode call keeps
     * the same argument construction.
     *
     * @param capacity the buffer capacity
     * @return a byte buffer with the requested capacity
     */
    protected ByteBuffer allocate(final int capacity) {
        return ByteBuffer.allocate(capacity);
    }

    @Test
    void testEncodeByteBufferObjectEmpty() throws EncoderException {
        final Object emptyByteBuffer = allocate(0);

        assertArrayEquals(new char[0], (char[]) new Hex().encode(emptyByteBuffer));
    }
}

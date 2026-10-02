package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.nio.ByteBuffer;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testDecodeByteBufferObjectEmpty {

    /**
     * Allocates the buffer used by the test.
     *
     * <p>The original Hex tests expose this helper so subclasses can switch to
     * direct buffers; this focused test keeps the same call site and argument.
     *
     * @param capacity the byte buffer capacity
     * @return a heap byte buffer with the requested capacity
     */
    protected ByteBuffer allocate(final int capacity) {
        return ByteBuffer.allocate(capacity);
    }

    @Test
    void testDecodeByteBufferObjectEmpty() throws DecoderException {
        assertArrayEquals(new byte[0], (byte[]) new Hex().decode((Object) allocate(0)));
    }
}

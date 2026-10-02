package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testDecodeByteBufferAllocatedButEmpty {

    protected ByteBuffer allocate(final int capacity) {
        return ByteBuffer.allocate(capacity);
    }

    @Test
    void testDecodeByteBufferAllocatedButEmpty() throws DecoderException {
        final ByteBuffer bb = allocate(10);
        bb.flip();

        assertArrayEquals(new byte[0], new Hex().decode(bb));
        assertEquals(0, bb.remaining());
    }
}

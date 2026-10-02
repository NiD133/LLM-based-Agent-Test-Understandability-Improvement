package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.nio.ByteBuffer;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testDecodeByteBufferEmpty {

    protected ByteBuffer allocate(final int capacity) {
        return ByteBuffer.allocate(capacity);
    }

    @Test
    void testDecodeByteBufferEmpty() throws DecoderException {
        ByteBuffer emptyBuffer = allocate(0);
        byte[] result = new Hex().decode(emptyBuffer);
        assertArrayEquals(new byte[0], result);
    }
}

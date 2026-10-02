package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

public class HexTest_testEncodeHexByteString_ByteBufferBoolean_ToLowerCase {

    /**
     * Mirrors the allocation hook from the original test so the ByteBuffer setup
     * remains explicit and easy to follow.
     *
     * @param capacity the requested buffer capacity
     * @return a heap-allocated byte buffer
     */
    protected ByteBuffer allocate(final int capacity) {
        return ByteBuffer.allocate(capacity);
    }

    @Test
    void testEncodeHexByteString_ByteBufferBoolean_ToLowerCase() {
        final ByteBuffer buffer = allocate(1);
        buffer.put((byte) 10);
        buffer.flip();

        assertEquals("0a", Hex.encodeHexString(buffer, true));
    }
}

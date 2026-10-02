package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

public class HexTest_testEncodeHexByteBufferEmpty {

    /**
     * Allocates a heap ByteBuffer with the given capacity.
     * Subclasses may override to use direct buffers instead.
     */
    protected ByteBuffer allocate(final int capacity) {
        return ByteBuffer.allocate(capacity);
    }

    /**
     * Verifies that encoding an empty ByteBuffer produces empty output for both
     * the static char-array form and the instance byte-array form of encodeHex.
     */
    @Test
    void testEncodeHexByteBufferEmpty() {
        ByteBuffer emptyBuffer = allocate(0);

        // Static Hex.encodeHex(ByteBuffer) should return an empty char array
        assertArrayEquals(new char[0], Hex.encodeHex(emptyBuffer));

        // Instance Hex#encode(ByteBuffer) should return an empty byte array
        assertArrayEquals(new byte[0], new Hex().encode(allocate(0)));
    }
}

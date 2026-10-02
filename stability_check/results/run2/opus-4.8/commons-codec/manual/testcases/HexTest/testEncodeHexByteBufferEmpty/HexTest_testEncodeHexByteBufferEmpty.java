package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

/**
 * Verifies that encoding an empty {@link ByteBuffer} yields empty results,
 * both for the static {@link Hex#encodeHex(ByteBuffer)} method and for the
 * instance {@link Hex#encode(ByteBuffer)} method.
 */
public class HexTest_testEncodeHexByteBufferEmpty {

    /**
     * Allocates a heap {@link ByteBuffer} of the given capacity.
     *
     * @param capacity the capacity of the buffer.
     * @return the allocated byte buffer.
     */
    protected ByteBuffer allocate(final int capacity) {
        return ByteBuffer.allocate(capacity);
    }

    @Test
    void testEncodeHexByteBufferEmpty() {
        final ByteBuffer emptyBuffer = allocate(0);

        // Encoding an empty buffer to hex characters produces no characters.
        assertArrayEquals(new char[0], Hex.encodeHex(emptyBuffer));

        // Encoding an empty buffer to hex bytes produces no bytes.
        assertArrayEquals(new byte[0], new Hex().encode(allocate(0)));
    }
}

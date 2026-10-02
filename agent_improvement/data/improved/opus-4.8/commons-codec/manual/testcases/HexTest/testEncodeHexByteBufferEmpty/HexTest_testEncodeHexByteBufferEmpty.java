package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

/**
 * Verifies that encoding an empty {@link ByteBuffer} to hexadecimal yields an
 * empty result, for both the static {@link Hex#encodeHex(ByteBuffer)} method
 * (returning {@code char[]}) and the instance {@link Hex#encode(ByteBuffer)}
 * method (returning {@code byte[]}).
 */
public class HexTest_testEncodeHexByteBufferEmpty {

    /**
     * Allocates a non-direct {@link ByteBuffer} with the given capacity.
     *
     * @param capacity the buffer capacity.
     * @return a newly allocated byte buffer.
     */
    private ByteBuffer allocate(final int capacity) {
        return ByteBuffer.allocate(capacity);
    }

    @Test
    void testEncodeHexByteBufferEmpty() {
        final ByteBuffer emptyBuffer = allocate(0);

        // Static encodeHex returns an empty char[] for an empty buffer.
        assertArrayEquals(new char[0], Hex.encodeHex(emptyBuffer));

        // Instance encode returns an empty byte[] for an empty buffer.
        assertArrayEquals(new byte[0], new Hex().encode(allocate(0)));
    }
}

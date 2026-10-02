package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

/**
 * Verifies that encoding an empty {@link ByteBuffer} to hexadecimal produces empty results.
 */
public class HexTest_testEncodeHexByteBufferEmpty {

    /** An empty byte buffer, i.e. one with zero remaining bytes to encode. */
    private static ByteBuffer emptyByteBuffer() {
        return ByteBuffer.allocate(0);
    }

    @Test
    void testEncodeHexByteBufferEmpty() {
        // Encoding an empty buffer yields no hexadecimal characters.
        assertArrayEquals(new char[0], Hex.encodeHex(emptyByteBuffer()));

        // The instance-based encoder likewise yields no bytes.
        assertArrayEquals(new byte[0], new Hex().encode(emptyByteBuffer()));
    }
}

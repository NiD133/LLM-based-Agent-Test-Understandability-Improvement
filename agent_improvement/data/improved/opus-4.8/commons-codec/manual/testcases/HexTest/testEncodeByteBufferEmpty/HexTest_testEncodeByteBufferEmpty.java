package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Hex#encode(ByteBuffer)} with an empty buffer.
 */
public class HexTest_testEncodeByteBufferEmpty {

    /**
     * Encoding an empty {@link ByteBuffer} should yield an empty byte array,
     * since there are no bytes to convert to hexadecimal characters.
     */
    @Test
    void testEncodeByteBufferEmpty() {
        final ByteBuffer emptyBuffer = ByteBuffer.allocate(0);

        final byte[] encoded = new Hex().encode(emptyBuffer);

        assertArrayEquals(new byte[0], encoded);
    }
}

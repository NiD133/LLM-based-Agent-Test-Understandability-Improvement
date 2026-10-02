package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Hex#encodeHex(ByteBuffer)} with a buffer that contains only zero bytes.
 */
public class HexTest_testEncodeHex_ByteBufferOfZeroes {

    /** Number of zero-valued bytes held by the buffer under test. */
    private static final int ZERO_BYTE_COUNT = 36;

    /**
     * Allocates a {@link ByteBuffer} of the given capacity. A freshly allocated
     * buffer is filled with zero bytes, so every byte read from it is {@code 0x00}.
     *
     * @param capacity the buffer capacity.
     * @return the byte buffer.
     */
    protected ByteBuffer allocate(final int capacity) {
        return ByteBuffer.allocate(capacity);
    }

    @Test
    void testEncodeHex_ByteBufferOfZeroes() {
        final char[] encoded = Hex.encodeHex(allocate(ZERO_BYTE_COUNT));

        // Each zero byte encodes to the two characters "00",
        // so 36 zero bytes produce a string of 72 '0' characters.
        final String expected = "000000000000000000000000000000000000000000000000000000000000000000000000";
        assertEquals(expected, new String(encoded));
    }
}

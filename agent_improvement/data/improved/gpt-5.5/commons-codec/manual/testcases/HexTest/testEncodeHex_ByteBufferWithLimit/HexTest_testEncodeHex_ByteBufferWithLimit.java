package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

public class HexTest_testEncodeHex_ByteBufferWithLimit {

    private static final int BUFFER_SIZE = 16;
    private static final int BYTE_PAIR_SIZE = 2;
    private static final int HEX_CHARS_PER_BYTE = 2;
    private static final String EXPECTED_HEX_FOR_ALL_BYTES = "000102030405060708090a0b0c0d0e0f";

    /**
     * Allocate a ByteBuffer.
     *
     * <p>The default implementation uses {@link ByteBuffer#allocate(int)}.
     * The method is overridden in AllocateDirectHexTest to use
     * {@link ByteBuffer#allocateDirect(int)}
     *
     * @param capacity the capacity
     * @return the byte buffer
     */
    protected ByteBuffer allocate(final int capacity) {
        return ByteBuffer.allocate(capacity);
    }

    @Test
    void testEncodeHex_ByteBufferWithLimit() {
        final ByteBuffer buffer = allocate(BUFFER_SIZE);
        for (int value = 0; value < BUFFER_SIZE; value++) {
            buffer.put((byte) value);
        }
        buffer.flip();

        for (int startByte = 0; startByte < BUFFER_SIZE - 1; startByte++) {
            buffer.position(startByte);
            buffer.limit(startByte + BYTE_PAIR_SIZE);

            final int firstHexChar = startByte * HEX_CHARS_PER_BYTE;
            final int hexCharCount = BYTE_PAIR_SIZE * HEX_CHARS_PER_BYTE;
            final String expectedHexPair = EXPECTED_HEX_FOR_ALL_BYTES.substring(firstHexChar, firstHexChar + hexCharCount);

            assertEquals(expectedHexPair, new String(Hex.encodeHex(buffer)));
            assertEquals(0, buffer.remaining());
        }
    }
}

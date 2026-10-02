package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Hex#encodeHex(ByteBuffer)} only encodes the bytes that
 * currently lie between the buffer's position and limit (i.e. the
 * {@link ByteBuffer#remaining() remaining} bytes), and that it fully consumes
 * those bytes afterwards.
 */
public class HexTest_testEncodeHex_ByteBufferWithLimit {

    /** Number of bytes placed into the buffer: values 0x00..0x0f. */
    private static final int BUFFER_SIZE = 16;

    /** Lower-case hex encoding of the bytes 0x00..0x0f. */
    private static final String FULL_HEX = "000102030405060708090a0b0c0d0e0f";

    /**
     * Allocates a heap-backed {@link ByteBuffer} of the given capacity.
     *
     * <p>Kept as an overridable method so subclasses can supply a direct buffer
     * (mirroring the structure of the original Hex test suite).</p>
     *
     * @param capacity the capacity.
     * @return the byte buffer.
     */
    protected ByteBuffer allocate(final int capacity) {
        return ByteBuffer.allocate(capacity);
    }

    @Test
    void testEncodeHex_ByteBufferWithLimit() {
        // Fill the buffer with the bytes 0x00, 0x01, ... 0x0f, then prepare it for reading.
        final ByteBuffer buffer = allocate(BUFFER_SIZE);
        for (int i = 0; i < BUFFER_SIZE; i++) {
            buffer.put((byte) i);
        }
        buffer.flip();

        // For each adjacent pair of bytes, restrict the buffer to a 2-byte window
        // and confirm encodeHex only encodes that window.
        for (int byteIndex = 0; byteIndex < BUFFER_SIZE - 1; byteIndex++) {
            buffer.position(byteIndex);
            buffer.limit(byteIndex + 2);

            // Two bytes -> four hex characters; locate the matching slice of FULL_HEX.
            final int hexStart = byteIndex * 2;
            final String expectedHexPair = FULL_HEX.substring(hexStart, hexStart + 4);

            assertEquals(expectedHexPair, new String(Hex.encodeHex(buffer)));
            // encodeHex must have consumed every remaining byte in the window.
            assertEquals(0, buffer.remaining());
        }
    }
}

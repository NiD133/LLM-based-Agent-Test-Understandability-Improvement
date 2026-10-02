package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

public class HexTest_testEncodeHex_ByteBufferWithLimit {

    /**
     * Allocates a heap ByteBuffer of the given capacity.
     * Subclasses may override to test direct buffers.
     */
    protected ByteBuffer allocate(final int capacity) {
        return ByteBuffer.allocate(capacity);
    }

    @Test
    void testEncodeHex_ByteBufferWithLimit() {
        // Fill a 16-byte buffer with sequential values 0x00..0x0f
        final ByteBuffer buffer = allocate(16);
        for (int i = 0; i < 16; i++) {
            buffer.put((byte) i);
        }
        buffer.flip();

        // The expected lowercase hex encoding of the full 16-byte sequence
        final String fullHex = "000102030405060708090a0b0c0d0e0f";
        final int windowSizeBytes = 2;
        final int hexCharsPerByte = 2;

        // Slide a 2-byte window over the buffer and verify that encodeHex
        // encodes only the bytes currently between position and limit.
        for (int byteIndex = 0; byteIndex < 15; byteIndex++) {
            buffer.position(byteIndex);
            buffer.limit(byteIndex + windowSizeBytes);

            final int hexStart = byteIndex * hexCharsPerByte;
            final int hexEnd = hexStart + windowSizeBytes * hexCharsPerByte;
            final String expectedSlice = fullHex.substring(hexStart, hexEnd);

            assertEquals(expectedSlice, new String(Hex.encodeHex(buffer)));
            // encodeHex must consume all remaining bytes (position advances to limit)
            assertEquals(0, buffer.remaining());
        }
    }
}

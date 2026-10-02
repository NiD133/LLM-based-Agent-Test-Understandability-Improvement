package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Hex#encodeHexString(ByteBuffer)} only encodes the bytes
 * that are currently "remaining" in the buffer (i.e. the bytes between the
 * buffer's position and its limit) and that it consumes them, leaving
 * {@link ByteBuffer#remaining()} at zero afterwards.
 */
public class HexTest_testEncodeHexByteString_ByteBufferOfZeroesWithLimit {

    @Test
    void testEncodeHexByteString_ByteBufferOfZeroesWithLimit() {
        // A freshly allocated buffer is filled with zero bytes. Each zero byte
        // is encoded as the two hex characters "00".
        final ByteBuffer buffer = ByteBuffer.allocate(36);

        // Encode the first 3 bytes (position 0 up to limit 3) -> "00" x 3.
        buffer.limit(3);
        assertEquals("000000", Hex.encodeHexString(buffer));
        // Encoding consumes the remaining bytes, so nothing is left.
        assertEquals(0, buffer.remaining());

        // Now encode only the bytes from position 1 to limit 3 (2 bytes) -> "00" x 2.
        buffer.position(1);
        buffer.limit(3);
        assertEquals("0000", Hex.encodeHexString(buffer));
        assertEquals(0, buffer.remaining());
    }
}

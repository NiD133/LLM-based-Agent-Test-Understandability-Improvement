package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link Hex#encodeHexString(ByteBuffer, boolean)} when only a slice of the
 * buffer is exposed via its position/limit window and lower-case output is requested.
 */
public class HexTest_testEncodeHexByteString_ByteBufferWithLimitBoolean_ToLowerCase {

    /**
     * Encoding must operate solely on the buffer's "remaining" window (the bytes between
     * position and limit), not on the whole backing array, and must consume that window.
     *
     * <p>The buffer below holds 4 bytes [0, 10, 0, 0] but exposes only the single byte at
     * index 1 (value 10 = 0x0a). Encoding it in lower-case should therefore yield "0a",
     * and afterwards the buffer should be fully consumed (no remaining bytes).</p>
     */
    @Test
    void testEncodeHexByteString_ByteBufferWithLimitBoolean_ToLowerCase() {
        final boolean toLowerCase = true;

        // Build a 4-byte buffer and expose only the byte at index 1 (value 0x0a).
        final ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.put(1, (byte) 10);
        buffer.position(1);
        buffer.limit(2);

        assertEquals("0a", Hex.encodeHexString(buffer, toLowerCase));
        assertEquals(0, buffer.remaining(), "encoding should consume the exposed window");
    }
}

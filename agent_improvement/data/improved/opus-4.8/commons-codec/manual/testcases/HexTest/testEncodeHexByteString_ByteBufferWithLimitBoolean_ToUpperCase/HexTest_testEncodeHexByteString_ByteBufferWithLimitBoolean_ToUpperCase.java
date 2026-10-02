package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Hex#encodeHexString(ByteBuffer, boolean)} encodes only the bytes
 * delimited by the buffer's current position and limit, and that it consumes those bytes.
 */
public class HexTest_testEncodeHexByteString_ByteBufferWithLimitBoolean_ToUpperCase {

    @Test
    void testEncodeHexByteString_ByteBufferWithLimitBoolean_ToUpperCase() {
        // Build a 4-byte buffer whose only non-zero byte (value 10 = 0x0A) sits at index 1.
        final ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.put(1, (byte) 10);

        // Restrict the readable window to a single byte: index 1 only (position=1, limit=2).
        buffer.position(1);
        buffer.limit(2);

        // Encoding with toLowerCase=false yields the upper-case hex of that single byte.
        assertEquals("0A", Hex.encodeHexString(buffer, false));

        // All readable bytes were consumed, so nothing remains.
        assertEquals(0, buffer.remaining());
    }
}

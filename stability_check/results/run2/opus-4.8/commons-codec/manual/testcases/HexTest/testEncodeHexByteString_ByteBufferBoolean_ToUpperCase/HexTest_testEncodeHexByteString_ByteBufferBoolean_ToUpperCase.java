package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Hex#encodeHexString(ByteBuffer, boolean)} produces
 * upper-case hexadecimal output when {@code toLowerCase} is {@code false}.
 */
public class HexTest_testEncodeHexByteString_ByteBufferBoolean_ToUpperCase {

    @Test
    void testEncodeHexByteString_ByteBufferBoolean_ToUpperCase() {
        // Build a buffer holding the single byte 0x0A and make it ready for reading.
        final ByteBuffer buffer = ByteBuffer.allocate(1);
        buffer.put((byte) 10);
        buffer.flip();

        // toLowerCase = false, so byte 0x0A must render as the upper-case string "0A".
        assertEquals("0A", Hex.encodeHexString(buffer, false));
    }
}

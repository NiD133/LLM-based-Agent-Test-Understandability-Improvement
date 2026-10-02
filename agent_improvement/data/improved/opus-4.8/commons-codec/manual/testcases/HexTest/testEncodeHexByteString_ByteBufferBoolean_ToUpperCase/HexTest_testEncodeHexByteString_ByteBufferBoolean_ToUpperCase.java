package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link Hex#encodeHexString(ByteBuffer, boolean)} when encoding to
 * upper-case hexadecimal (i.e. {@code toLowerCase = false}).
 */
public class HexTest_testEncodeHexByteString_ByteBufferBoolean_ToUpperCase {

    @Test
    void testEncodeHexByteString_ByteBufferBoolean_ToUpperCase() {
        // Wrap the single byte 0x0A in a buffer ready for reading.
        final ByteBuffer buffer = ByteBuffer.allocate(1);
        buffer.put((byte) 10);
        buffer.flip();

        // toLowerCase = false -> upper-case hex digits, so 0x0A renders as "0A".
        final boolean toLowerCase = false;
        assertEquals("0A", Hex.encodeHexString(buffer, toLowerCase));
    }
}

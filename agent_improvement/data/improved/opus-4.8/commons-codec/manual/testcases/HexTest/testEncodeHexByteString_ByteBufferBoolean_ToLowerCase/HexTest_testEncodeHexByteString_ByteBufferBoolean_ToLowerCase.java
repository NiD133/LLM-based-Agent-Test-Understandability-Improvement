package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Hex#encodeHexString(ByteBuffer, boolean)} produces a
 * lower-case hexadecimal String for the remaining bytes of a ByteBuffer.
 */
public class HexTest_testEncodeHexByteString_ByteBufferBoolean_ToLowerCase {

    /**
     * Allocates a ByteBuffer holding a single byte and prepares it for reading.
     *
     * @param value the byte value to store
     * @return a ByteBuffer positioned at the start, ready to be read
     */
    private static ByteBuffer singleByteBuffer(final byte value) {
        final ByteBuffer buffer = ByteBuffer.allocate(1);
        buffer.put(value);
        buffer.flip();
        return buffer;
    }

    @Test
    void testEncodeHexByteString_ByteBufferBoolean_ToLowerCase() {
        final ByteBuffer input = singleByteBuffer((byte) 10);

        // The byte 10 (0x0A) must encode to the lower-case hex string "0a".
        final String encoded = Hex.encodeHexString(input, true);

        assertEquals("0a", encoded);
    }
}

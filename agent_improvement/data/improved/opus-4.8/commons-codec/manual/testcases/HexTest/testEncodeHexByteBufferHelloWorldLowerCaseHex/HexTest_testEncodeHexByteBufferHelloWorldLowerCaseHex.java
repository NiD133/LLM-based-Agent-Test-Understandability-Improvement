package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Hex#encodeHex(ByteBuffer)} and {@link Hex#encodeHex(ByteBuffer, boolean)}
 * by hex-encoding the UTF-8 bytes of "Hello World".
 */
public class HexTest_testEncodeHexByteBufferHelloWorldLowerCaseHex {

    /** Plain-text input shared by every case. */
    private static final String INPUT = "Hello World";

    /** Expected lower-case hex of the UTF-8 bytes of {@link #INPUT}. */
    private static final String EXPECTED_LOWER_CASE = "48656c6c6f20576f726c64";

    /** Expected upper-case hex, derived from the lower-case form. */
    private static final String EXPECTED_UPPER_CASE = EXPECTED_LOWER_CASE.toUpperCase();

    /**
     * Wraps the UTF-8 bytes of the given string in a {@link ByteBuffer} that is
     * ready to be read (flipped so position is 0 and limit is the byte count).
     */
    private static ByteBuffer toUtf8ByteBuffer(final String string) {
        final byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
        final ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        buffer.put(bytes);
        buffer.flip();
        return buffer;
    }

    @Test
    void testEncodeHexByteBufferHelloWorldLowerCaseHex() {
        final ByteBuffer buffer = toUtf8ByteBuffer(INPUT);

        // Default overload encodes as lower-case hex.
        char[] actual = Hex.encodeHex(buffer);
        assertEquals(EXPECTED_LOWER_CASE, new String(actual));
        assertEquals(0, buffer.remaining(), "all bytes should be consumed");

        // Explicit lower-case (toLowerCase = true).
        buffer.flip();
        actual = Hex.encodeHex(buffer, true);
        assertEquals(EXPECTED_LOWER_CASE, new String(actual));
        assertEquals(0, buffer.remaining(), "all bytes should be consumed");

        // Explicit upper-case (toLowerCase = false).
        buffer.flip();
        actual = Hex.encodeHex(buffer, false);
        assertEquals(EXPECTED_UPPER_CASE, new String(actual));
        assertEquals(0, buffer.remaining(), "all bytes should be consumed");
    }
}

package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class HexTest_testEncodeHexByteBufferHelloWorldUpperCaseHex {

    /** The bytes "Hello World" encoded as hexadecimal, in upper case. */
    private static final String EXPECTED_UPPER_CASE = "48656C6C6F20576F726C64";

    /** The same hexadecimal value in lower case (the default encoding). */
    private static final String EXPECTED_LOWER_CASE = EXPECTED_UPPER_CASE.toLowerCase();

    /**
     * Wraps the UTF-8 bytes of the given string in a ByteBuffer that is ready to be read,
     * i.e. positioned at the start with all bytes remaining.
     */
    private static ByteBuffer utf8Buffer(final String text) {
        final byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        final ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        buffer.put(bytes);
        buffer.flip();
        return buffer;
    }

    @Test
    void testEncodeHexByteBufferHelloWorldUpperCaseHex() {
        final ByteBuffer buffer = utf8Buffer("Hello World");

        // No case argument: defaults to lower case and consumes all remaining bytes.
        final char[] defaultCase = Hex.encodeHex(buffer);
        assertEquals(EXPECTED_LOWER_CASE, new String(defaultCase));
        assertEquals(0, buffer.remaining());

        // toLowerCase = true: explicit lower case.
        buffer.flip();
        final char[] lowerCase = Hex.encodeHex(buffer, true);
        assertEquals(EXPECTED_LOWER_CASE, new String(lowerCase));
        assertEquals(0, buffer.remaining());

        // toLowerCase = false: upper case.
        buffer.flip();
        final char[] upperCase = Hex.encodeHex(buffer, false);
        assertEquals(EXPECTED_UPPER_CASE, new String(upperCase));
        assertEquals(0, buffer.remaining());
    }
}

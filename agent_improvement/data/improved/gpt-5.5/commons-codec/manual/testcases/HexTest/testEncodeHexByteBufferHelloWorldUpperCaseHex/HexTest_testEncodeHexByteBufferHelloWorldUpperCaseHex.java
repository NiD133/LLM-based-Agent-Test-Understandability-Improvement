package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class HexTest_testEncodeHexByteBufferHelloWorldUpperCaseHex {

    private static final String SOURCE_TEXT = "Hello World";
    private static final String EXPECTED_UPPER_CASE_HEX = "48656C6C6F20576F726C64";

    /**
     * Allocate a ByteBuffer.
     *
     * <p>The default implementation uses {@link ByteBuffer#allocate(int)}.
     * The method is overridden in AllocateDirectHexTest to use
     * {@link ByteBuffer#allocateDirect(int)}
     *
     * @param capacity the capacity
     * @return the byte buffer
     */
    protected ByteBuffer allocate(final int capacity) {
        return ByteBuffer.allocate(capacity);
    }

    /**
     * Encodes the given string into a byte buffer using the UTF-8 charset.
     *
     * <p>The buffer is allocated using {@link #allocate(int)}.
     *
     * @param string the String to encode
     * @return the byte buffer
     */
    private ByteBuffer getByteBufferUtf8(final String string) {
        final byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
        final ByteBuffer byteBuffer = allocate(bytes.length);
        byteBuffer.put(bytes);
        byteBuffer.flip();
        return byteBuffer;
    }

    @Test
    void testEncodeHexByteBufferHelloWorldUpperCaseHex() {
        final ByteBuffer buffer = getByteBufferUtf8(SOURCE_TEXT);
        char[] actual;

        actual = Hex.encodeHex(buffer);
        assertEquals(EXPECTED_UPPER_CASE_HEX.toLowerCase(), new String(actual));
        assertEquals(0, buffer.remaining());

        buffer.flip();
        actual = Hex.encodeHex(buffer, true);
        assertEquals(EXPECTED_UPPER_CASE_HEX.toLowerCase(), new String(actual));
        assertEquals(0, buffer.remaining());

        buffer.flip();
        actual = Hex.encodeHex(buffer, false);
        assertEquals(EXPECTED_UPPER_CASE_HEX, new String(actual));
        assertEquals(0, buffer.remaining());
    }
}

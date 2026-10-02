package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class HexTest_testEncodeHexByteBufferHelloWorldLowerCaseHex {

    private static final String SOURCE_TEXT = "Hello World";
    private static final String LOWER_CASE_HEX = "48656c6c6f20576f726c64";

    protected ByteBuffer allocate(final int capacity) {
        return ByteBuffer.allocate(capacity);
    }

    private ByteBuffer getByteBufferUtf8(final String string) {
        final byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
        final ByteBuffer buffer = allocate(bytes.length);
        buffer.put(bytes);
        buffer.flip();
        return buffer;
    }

    private void assertEncodedHexAndBufferConsumed(final String expectedHex, final char[] actualHex,
            final ByteBuffer sourceBuffer) {
        assertEquals(expectedHex, new String(actualHex));
        assertEquals(0, sourceBuffer.remaining());
    }

    @Test
    void testEncodeHexByteBufferHelloWorldLowerCaseHex() {
        final ByteBuffer buffer = getByteBufferUtf8(SOURCE_TEXT);

        assertEncodedHexAndBufferConsumed(LOWER_CASE_HEX, Hex.encodeHex(buffer), buffer);

        buffer.flip();
        assertEncodedHexAndBufferConsumed(LOWER_CASE_HEX, Hex.encodeHex(buffer, true), buffer);

        buffer.flip();
        assertEncodedHexAndBufferConsumed(LOWER_CASE_HEX.toUpperCase(), Hex.encodeHex(buffer, false), buffer);
    }
}

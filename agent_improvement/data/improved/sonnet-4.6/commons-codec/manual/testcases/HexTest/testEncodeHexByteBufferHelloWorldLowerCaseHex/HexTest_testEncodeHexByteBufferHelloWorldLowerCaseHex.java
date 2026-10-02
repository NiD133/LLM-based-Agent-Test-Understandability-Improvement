package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

public class HexTest_testEncodeHexByteBufferHelloWorldLowerCaseHex {

    /**
     * Allocates a heap ByteBuffer. Subclasses may override to use direct allocation.
     */
    protected ByteBuffer allocate(final int capacity) {
        return ByteBuffer.allocate(capacity);
    }

    /**
     * Returns a ByteBuffer whose content is the UTF-8 encoding of {@code string},
     * positioned at the start (ready for reading).
     */
    private ByteBuffer getByteBufferUtf8(final String string) {
        final byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
        final ByteBuffer bb = allocate(bytes.length);
        bb.put(bytes);
        bb.flip();
        return bb;
    }

    @Test
    void testEncodeHexByteBufferHelloWorldLowerCaseHex() {
        final ByteBuffer b = getByteBufferUtf8("Hello World");
        final String lowerCaseHex = "48656c6c6f20576f726c64";

        // Default encodeHex produces lower-case
        char[] actual = Hex.encodeHex(b);
        assertEquals(lowerCaseHex, new String(actual));
        assertEquals(0, b.remaining());

        // Explicit lower-case flag
        b.flip();
        actual = Hex.encodeHex(b, true);
        assertEquals(lowerCaseHex, new String(actual));
        assertEquals(0, b.remaining());

        // Explicit upper-case flag
        b.flip();
        actual = Hex.encodeHex(b, false);
        assertEquals(lowerCaseHex.toUpperCase(), new String(actual));
        assertEquals(0, b.remaining());
    }
}

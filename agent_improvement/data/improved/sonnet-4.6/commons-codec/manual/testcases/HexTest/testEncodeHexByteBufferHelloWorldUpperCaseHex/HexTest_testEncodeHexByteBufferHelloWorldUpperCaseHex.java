package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

public class HexTest_testEncodeHexByteBufferHelloWorldUpperCaseHex {

    private ByteBuffer getByteBufferUtf8(final String string) {
        final byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
        final ByteBuffer bb = ByteBuffer.allocate(bytes.length);
        bb.put(bytes);
        bb.flip();
        return bb;
    }

    @Test
    void testEncodeHexByteBufferHelloWorldUpperCaseHex() {
        final ByteBuffer b = getByteBufferUtf8("Hello World");
        final String expectedUpperCase = "48656C6C6F20576F726C64";
        final String expectedLowerCase = expectedUpperCase.toLowerCase();
        char[] actual;

        // Default encodeHex produces lower-case
        actual = Hex.encodeHex(b);
        assertEquals(expectedLowerCase, new String(actual));
        assertEquals(0, b.remaining());

        // Explicit toLowerCase=true also produces lower-case
        b.flip();
        actual = Hex.encodeHex(b, true);
        assertEquals(expectedLowerCase, new String(actual));
        assertEquals(0, b.remaining());

        // toLowerCase=false produces upper-case
        b.flip();
        actual = Hex.encodeHex(b, false);
        assertEquals(expectedUpperCase, new String(actual));
        assertEquals(0, b.remaining());
    }
}

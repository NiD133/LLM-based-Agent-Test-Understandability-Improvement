package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class Base16Test_testObjectEncode {

    private static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;
    private static final String PLAIN_TEXT = "Hello World";
    private static final String ENCODED_HELLO_WORLD = "48656C6C6F20576F726C64";

    @Test
    void testObjectEncode() {
        final Base16 base16 = new Base16();
        final byte[] plainTextBytes = PLAIN_TEXT.getBytes(CHARSET_UTF8);
        final String encodedText = new String(base16.encode(plainTextBytes));

        assertEquals(encodedText, ENCODED_HELLO_WORLD);
    }
}

package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class Base16Test_testObjectDecodeWithValidParameter {

    private static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;

    @Test
    void testObjectDecodeWithValidParameter() throws Exception {
        final String original = "Hello World!";
        final Object encoded = new Base16().encode(original.getBytes(CHARSET_UTF8));

        final Base16 base16 = new Base16();
        final Object decoded = base16.decode(encoded);
        final byte[] decodedBytes = (byte[]) decoded;
        final String decodedText = new String(decodedBytes);

        assertEquals(original, decodedText, "dest string does not equal original");
    }
}

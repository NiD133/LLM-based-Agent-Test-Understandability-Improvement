package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class Base58Test_testObjectDecodeWithValidParameter {

    private static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;
    private static final String ORIGINAL_TEXT = "Hello World!";

    @Test
    void testObjectDecodeWithValidParameter() throws Exception {
        final Object encoded = new Base58().encode(ORIGINAL_TEXT.getBytes(CHARSET_UTF8));

        final Base58 base58 = new Base58();
        final Object decoded = base58.decode(encoded);
        final byte[] decodedBytes = (byte[]) decoded;
        final String decodedText = new String(decodedBytes);

        assertEquals(ORIGINAL_TEXT, decodedText, "dest string does not equal original");
    }
}

package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class Base16Test_testStringToByteVariations {

    // Base16 (hex) encoding of the UTF-8 bytes for "Hello World"
    private static final String BASE16_HELLO_WORLD = "48656C6C6F20576F726C64";

    @Test
    void testStringToByteVariations() throws DecoderException {
        final Base16 base16 = new Base16();
        final String encodedHelloWorld = BASE16_HELLO_WORLD;
        final String emptyString = "";
        final String nullString = null;

        // Decode a non-empty Base16 string to bytes, then to UTF-8 text
        assertEquals("Hello World", StringUtils.newStringUtf8(base16.decode(encodedHelloWorld)),
                "StringToByte Hello World");
        assertEquals("Hello World", StringUtils.newStringUtf8((byte[]) new Base16().decode((Object) encodedHelloWorld)),
                "StringToByte Hello World");
        assertEquals("Hello World", StringUtils.newStringUtf8(new Base16().decode(encodedHelloWorld)),
                "StringToByte static Hello World");

        // Decoding an empty string should yield an empty byte array, which converts to ""
        assertEquals("", StringUtils.newStringUtf8(new Base16().decode(emptyString)),
                "StringToByte \"\"");
        assertEquals("", StringUtils.newStringUtf8(new Base16().decode(emptyString)),
                "StringToByte static \"\"");

        // Decoding a null string should yield a null byte array, which converts to null
        assertNull(StringUtils.newStringUtf8(new Base16().decode(nullString)),
                "StringToByte null");
        assertNull(StringUtils.newStringUtf8(new Base16().decode(nullString)),
                "StringToByte static null");
    }
}

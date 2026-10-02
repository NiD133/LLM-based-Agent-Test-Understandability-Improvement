package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class Base16Test_testStringToByteVariations {

    private static final String HELLO_WORLD = "Hello World";
    private static final String HELLO_WORLD_BASE16 = "48656C6C6F20576F726C64";
    private static final String EMPTY_BASE16 = "";
    private static final String NULL_BASE16 = null;

    @Test
    void testStringToByteVariations() throws DecoderException {
        final Base16 base16 = new Base16();

        assertEquals(HELLO_WORLD, StringUtils.newStringUtf8(base16.decode(HELLO_WORLD_BASE16)), "StringToByte Hello World");
        assertEquals(HELLO_WORLD, StringUtils.newStringUtf8((byte[]) new Base16().decode((Object) HELLO_WORLD_BASE16)), "StringToByte Hello World");
        assertEquals(HELLO_WORLD, StringUtils.newStringUtf8(new Base16().decode(HELLO_WORLD_BASE16)), "StringToByte static Hello World");

        assertEquals(EMPTY_BASE16, StringUtils.newStringUtf8(new Base16().decode(EMPTY_BASE16)), "StringToByte \"\"");
        assertEquals(EMPTY_BASE16, StringUtils.newStringUtf8(new Base16().decode(EMPTY_BASE16)), "StringToByte static \"\"");

        assertNull(StringUtils.newStringUtf8(new Base16().decode(NULL_BASE16)), "StringToByte null");
        assertNull(StringUtils.newStringUtf8(new Base16().decode(NULL_BASE16)), "StringToByte static null");
    }
}

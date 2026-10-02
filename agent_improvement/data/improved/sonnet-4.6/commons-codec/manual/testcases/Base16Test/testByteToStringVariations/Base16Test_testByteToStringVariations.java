package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class Base16Test_testByteToStringVariations {

    @Test
    void testByteToStringVariations() {
        final Base16 base16 = new Base16();
        final byte[] helloWorldBytes = StringUtils.getBytesUtf8("Hello World");
        final byte[] emptyBytes = {};
        final byte[] nullBytes = null;

        assertEquals("48656C6C6F20576F726C64", base16.encodeToString(helloWorldBytes), "byteToString Hello World");
        assertEquals("48656C6C6F20576F726C64", StringUtils.newStringUtf8(new Base16().encode(helloWorldBytes)), "byteToString static Hello World");
        assertEquals("", base16.encodeToString(emptyBytes), "byteToString \"\"");
        assertEquals("", StringUtils.newStringUtf8(new Base16().encode(emptyBytes)), "byteToString static \"\"");
        assertNull(base16.encodeToString(nullBytes), "byteToString null");
        assertNull(StringUtils.newStringUtf8(new Base16().encode(nullBytes)), "byteToString static null");
    }
}

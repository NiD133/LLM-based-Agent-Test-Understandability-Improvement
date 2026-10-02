package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Base16Test_testBase16 {

    /**
     * Verifies that Base16 (upper-case hex) correctly encodes and then decodes
     * the ASCII string "Hello World".
     *
     * "Hello World" in UTF-8 bytes: 48 65 6C 6C 6F 20 57 6F 72 6C 64
     * Expected Base16 encoding:     48656C6C6F20576F726C64
     */
    @Test
    void testBase16() {
        final String content = "Hello World";

        // Encode to Base16
        final byte[] encodedBytes = new Base16().encode(StringUtils.getBytesUtf8(content));
        final String encodedContent = StringUtils.newStringUtf8(encodedBytes);
        assertEquals("48656C6C6F20576F726C64", encodedContent, "encoding hello world");

        // Decode back to original string
        final byte[] decodedBytes = new Base16().decode(encodedBytes);
        final String decodedContent = StringUtils.newStringUtf8(decodedBytes);
        assertEquals(content, decodedContent, "decoding hello world");
    }
}

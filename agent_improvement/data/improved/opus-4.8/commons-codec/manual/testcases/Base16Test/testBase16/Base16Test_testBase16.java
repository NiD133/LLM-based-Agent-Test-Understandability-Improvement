package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Base16} can round-trip a string: encoding the bytes of
 * "Hello World" yields the expected hexadecimal text, and decoding that text
 * restores the original string.
 */
public class Base16Test_testBase16 {

    private static final String PLAIN_TEXT = "Hello World";

    /** Hexadecimal (Base16) encoding of the UTF-8 bytes of {@link #PLAIN_TEXT}. */
    private static final String EXPECTED_HEX = "48656C6C6F20576F726C64";

    @Test
    void testBase16() {
        final Base16 base16 = new Base16();

        // Encode: plain text bytes -> uppercase hexadecimal string.
        final byte[] encodedBytes = base16.encode(StringUtils.getBytesUtf8(PLAIN_TEXT));
        final String encodedContent = StringUtils.newStringUtf8(encodedBytes);
        assertEquals(EXPECTED_HEX, encodedContent, "encoding hello world");

        // Decode: hexadecimal bytes -> original plain text.
        final byte[] decodedBytes = base16.decode(encodedBytes);
        final String decodedContent = StringUtils.newStringUtf8(decodedBytes);
        assertEquals(PLAIN_TEXT, decodedContent, "decoding hello world");
    }
}

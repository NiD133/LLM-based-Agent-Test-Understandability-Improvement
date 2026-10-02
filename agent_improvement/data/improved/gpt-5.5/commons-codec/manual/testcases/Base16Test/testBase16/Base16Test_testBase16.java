package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Base16Test_testBase16 {

    private static final String PLAIN_TEXT = "Hello World";
    private static final String BASE16_ENCODED_TEXT = "48656C6C6F20576F726C64";

    /**
     * Verifies that Base16 encodes "Hello World" to its expected hexadecimal
     * representation and decodes it back to the original text.
     */
    @Test
    void testBase16() {
        final byte[] encodedBytes = new Base16().encode(StringUtils.getBytesUtf8(PLAIN_TEXT));
        final String encodedContent = StringUtils.newStringUtf8(encodedBytes);
        assertEquals(BASE16_ENCODED_TEXT, encodedContent, "encoding hello world");

        final byte[] decodedBytes = new Base16().decode(encodedBytes);
        final String decodedContent = StringUtils.newStringUtf8(decodedBytes);
        assertEquals(PLAIN_TEXT, decodedContent, "decoding hello world");
    }
}

package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Base58Test_testBase58 {

    @Test
    void testBase58() {
        final String input = "Hello World";
        final byte[] encodedBytes = new Base58().encode(StringUtils.getBytesUtf8(input));
        final String encodedString = StringUtils.newStringUtf8(encodedBytes);
        assertEquals("JxF12TrwUP45BMd", encodedString, "encoding hello world");

        final byte[] decodedBytes = new Base58().decode(encodedBytes);
        final String decodedString = StringUtils.newStringUtf8(decodedBytes);
        assertEquals(input, decodedString, "decoding hello world");
    }
}

package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Base58Test_testBase58 {

    @Test
    void testBase58() {
        final String content = "Hello World";

        final byte[] encodedBytes = new Base58().encode(StringUtils.getBytesUtf8(content));
        final String encodedContent = StringUtils.newStringUtf8(encodedBytes);
        assertEquals("JxF12TrwUP45BMd", encodedContent, "encoding hello world");

        final byte[] decodedBytes = new Base58().decode(encodedBytes);
        final String decodedContent = StringUtils.newStringUtf8(decodedBytes);
        assertEquals(content, decodedContent, "decoding hello world");
    }
}

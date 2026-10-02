package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class Base16Test_testObjectEncodeWithValidParameter {

    private static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;

    @Test
    void testObjectEncodeWithValidParameter() throws Exception {
        final String original = "Hello World!";
        final Object originalBytes = original.getBytes(CHARSET_UTF8);

        final Object encodedBytes = new Base16().encode(originalBytes);
        final byte[] decodedBytes = new Base16().decode((byte[]) encodedBytes);
        final String decoded = new String(decodedBytes);

        assertEquals(original, decoded, "dest string does not equal original");
    }
}

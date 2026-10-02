package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class Base58Test_testObjectDecodeWithValidParameter {

    @Test
    void testObjectDecodeWithValidParameter() throws Exception {
        final String original = "Hello World!";

        // Encode the original string's bytes to a Base58 Object
        final Object encoded = new Base58().encode(original.getBytes(StandardCharsets.UTF_8));

        // Decode the Base58 Object back to bytes and reconstruct the string
        final byte[] decodedBytes = (byte[]) new Base58().decode(encoded);
        final String decoded = new String(decodedBytes);

        assertEquals(original, decoded, "dest string does not equal original");
    }
}

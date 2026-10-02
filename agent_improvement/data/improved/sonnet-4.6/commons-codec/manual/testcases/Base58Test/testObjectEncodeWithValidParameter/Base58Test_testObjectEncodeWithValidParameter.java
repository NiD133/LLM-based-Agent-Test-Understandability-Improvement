package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class Base58Test_testObjectEncodeWithValidParameter {

    @Test
    void testObjectEncodeWithValidParameter() throws Exception {
        final String original = "Hello World!";
        final Object inputBytes = original.getBytes(StandardCharsets.UTF_8);

        // Encode via the Object-typed overload, then decode back to bytes
        final Object encodedBytes = new Base58().encode(inputBytes);
        final byte[] decodedBytes = new Base58().decode((byte[]) encodedBytes);

        final String decoded = new String(decodedBytes);
        assertEquals(original, decoded, "dest string does not equal original");
    }
}

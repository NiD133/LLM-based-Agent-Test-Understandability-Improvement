package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class Base16Test_testObjectEncodeWithValidParameter {

    @Test
    void testObjectEncodeWithValidParameter() throws Exception {
        // Encode a byte[] passed as Object, then decode and verify round-trip integrity
        final String original = "Hello World!";
        final Object inputBytes = original.getBytes(StandardCharsets.UTF_8);

        final Object encodedBytes = new Base16().encode(inputBytes);
        final byte[] decodedBytes = new Base16().decode((byte[]) encodedBytes);
        final String roundTripped = new String(decodedBytes);

        assertEquals(original, roundTripped, "dest string does not equal original");
    }
}

package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Base16#encode(Object)} followed by {@link Base16#decode(byte[])}
 * round-trips a byte array back to its original content.
 */
public class Base16Test_testObjectEncodeWithValidParameter {

    @Test
    void testObjectEncodeWithValidParameter() throws Exception {
        final String original = "Hello World!";
        final Object originalBytes = original.getBytes(StandardCharsets.UTF_8);

        // Encode the bytes to Base16, then decode them back.
        final Object encoded = new Base16().encode(originalBytes);
        final byte[] decoded = new Base16().decode((byte[]) encoded);

        final String roundTripped = new String(decoded);
        assertEquals(original, roundTripped, "decoded string does not equal original");
    }
}

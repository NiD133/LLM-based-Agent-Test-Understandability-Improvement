package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Base16#decode(Object)} accepts the {@code Object} produced by
 * {@link Base16#encode(byte[])} and round-trips it back to the original bytes.
 */
public class Base16Test_testObjectDecodeWithValidParameter {

    @Test
    void testObjectDecodeWithValidParameter() throws Exception {
        final String original = "Hello World!";

        // Encode the original text, keeping the result as an Object to exercise the
        // Object-based decode(Object) overload.
        final Object encoded = new Base16().encode(original.getBytes(StandardCharsets.UTF_8));

        final Object decoded = new Base16().decode(encoded);

        final String roundTripped = new String((byte[]) decoded);
        assertEquals(original, roundTripped, "decoded text should equal the original");
    }
}

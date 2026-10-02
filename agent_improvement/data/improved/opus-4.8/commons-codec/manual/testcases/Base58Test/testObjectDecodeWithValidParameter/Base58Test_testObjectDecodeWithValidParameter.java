package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Base58#decode(Object)} reverses {@link Base58#encode(Object)},
 * so that encoding a string and then decoding the result yields the original bytes.
 */
public class Base58Test_testObjectDecodeWithValidParameter {

    @Test
    void testObjectDecodeWithValidParameter() throws Exception {
        final String originalText = "Hello World!";

        // Encode the original bytes, then decode the resulting Base58 data back to bytes.
        final Object encoded = new Base58().encode(originalText.getBytes(StandardCharsets.UTF_8));
        final Object decoded = new Base58().decode(encoded);

        final String roundTripped = new String((byte[]) decoded);
        assertEquals(originalText, roundTripped, "decoded text should equal the original text");
    }
}

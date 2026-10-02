package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Base58#encode(Object)} accepts a {@code byte[]} payload and that the
 * Base58-encoded result decodes back to the original bytes (a round-trip check).
 */
public class Base58Test_testObjectEncodeWithValidParameter {

    @Test
    void testObjectEncodeWithValidParameter() throws Exception {
        // Given: the original text as UTF-8 bytes, passed as a generic Object.
        final String originalText = "Hello World!";
        final Object originalBytes = originalText.getBytes(StandardCharsets.UTF_8);

        // When: encoding the bytes to Base58 and then decoding the result back.
        final Object encoded = new Base58().encode(originalBytes);
        final byte[] decodedBytes = new Base58().decode((byte[]) encoded);
        final String decodedText = new String(decodedBytes);

        // Then: the decoded text matches the original.
        assertEquals(originalText, decodedText, "decoded string does not equal original");
    }
}

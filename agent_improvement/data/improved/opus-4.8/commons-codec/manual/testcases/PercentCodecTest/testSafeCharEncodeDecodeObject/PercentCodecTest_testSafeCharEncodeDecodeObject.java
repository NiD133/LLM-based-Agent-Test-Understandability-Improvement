package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link PercentCodec}, when configured with no extra
 * "always encode" characters, leaves URI-safe characters untouched through a
 * full encode-then-decode round trip via the {@code Object} based API.
 */
public class PercentCodecTest_testSafeCharEncodeDecodeObject {

    @Test
    void testSafeCharEncodeDecodeObject() throws Exception {
        // A PercentCodec with no extra always-encode chars (null) and no
        // plus-for-space handling. With this configuration, the only character
        // ever escaped is the reserved '%'.
        final PercentCodec percentCodec = new PercentCodec(null, true);

        // Every character here is URI-safe, so encoding must leave it unchanged.
        final String safeCharacters = "abc123_-.*";
        final byte[] inputBytes = safeCharacters.getBytes(StandardCharsets.UTF_8);

        // Encode the bytes through the Object-based API.
        final Object encoded = percentCodec.encode((Object) inputBytes);
        final String encodedText = new String((byte[]) encoded, StandardCharsets.UTF_8);

        // Decode the encoded result back through the Object-based API.
        final Object decoded = percentCodec.decode(encoded);
        final String decodedText = new String((byte[]) decoded, StandardCharsets.UTF_8);

        // Safe characters pass through encoding and decoding unchanged.
        assertEquals(safeCharacters, encodedText, "Safe characters must not be altered when encoded");
        assertEquals(safeCharacters, decodedText, "Decoding must restore the original safe characters");
    }
}

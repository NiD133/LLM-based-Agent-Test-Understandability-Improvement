package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link URLCodec#decode(String, String)} tolerates a {@code null} input string.
 */
public class URLCodecTest_testDecodeStringWithNull {

    @Test
    void decodingNullStringReturnsNull() throws Exception {
        final URLCodec urlCodec = new URLCodec();

        // The charset name is irrelevant here: a null input short-circuits to a null result
        // before any charset handling takes place.
        final String decoded = urlCodec.decode(null, "charset");

        assertNull(decoded, "Decoding a null string should return null");
    }
}

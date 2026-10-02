package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link URLCodec} leaves URL-safe characters untouched during a
 * full encode/decode round-trip.
 *
 * <p>The www-form-url specification treats ASCII letters, digits, and the
 * characters {@code - _ . *} as safe, so they must pass through both
 * {@link URLCodec#encode(String)} and {@link URLCodec#decode(String)}
 * unchanged.</p>
 */
public class URLCodecTest_testSafeCharEncodeDecode {

    /** Input made up exclusively of URL-safe characters: letters, digits, and {@code - _ . *}. */
    private static final String SAFE_CHARS = "abc123_-.*";

    @Test
    void testSafeCharEncodeDecode() throws Exception {
        final URLCodec urlCodec = new URLCodec();

        final String encoded = urlCodec.encode(SAFE_CHARS);
        assertEquals(SAFE_CHARS, encoded, "Safe chars should be encoded unchanged");

        final String decoded = urlCodec.decode(encoded);
        assertEquals(SAFE_CHARS, decoded, "Safe chars should be decoded unchanged");
    }
}

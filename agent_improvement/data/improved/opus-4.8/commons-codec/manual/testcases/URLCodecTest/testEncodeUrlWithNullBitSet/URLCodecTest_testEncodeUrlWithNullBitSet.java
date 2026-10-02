package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link URLCodec#encodeUrl(java.util.BitSet, byte[])} falls back to the
 * default www-form-url safe character set when the {@code BitSet} argument is {@code null},
 * and that the resulting encoded string can be decoded back to the original text.
 */
public class URLCodecTest_testEncodeUrlWithNullBitSet {

    @Test
    void testEncodeUrlWithNullBitSet() throws Exception {
        final URLCodec urlCodec = new URLCodec();
        final String plainText = "Hello there!";

        // A null BitSet makes encodeUrl use the default safe-character set:
        // space becomes '+' and '!' is escaped as %21.
        final byte[] encodedBytes = URLCodec.encodeUrl(null, plainText.getBytes(StandardCharsets.UTF_8));
        final String encoded = new String(encodedBytes);

        assertEquals("Hello+there%21", encoded, "Basic URL encoding test");
        assertEquals(plainText, urlCodec.decode(encoded), "Basic URL decoding test");
    }
}

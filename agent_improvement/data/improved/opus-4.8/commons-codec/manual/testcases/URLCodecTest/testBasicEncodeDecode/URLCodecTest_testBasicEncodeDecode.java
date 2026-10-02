package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link URLCodec} performs basic www-form-urlencoded
 * encoding and decoding, and that decoding restores the original text.
 */
public class URLCodecTest_testBasicEncodeDecode {

    @Test
    void testBasicEncodeDecode() throws Exception {
        final URLCodec urlCodec = new URLCodec();
        final String plainText = "Hello there!";

        // Spaces become '+' and '!' is escaped to its %-encoded form.
        final String encoded = urlCodec.encode(plainText);
        assertEquals("Hello+there%21", encoded, "Basic URL encoding test");

        // Decoding the encoded value must yield the original text.
        assertEquals(plainText, urlCodec.decode(encoded), "Basic URL decoding test");
    }
}

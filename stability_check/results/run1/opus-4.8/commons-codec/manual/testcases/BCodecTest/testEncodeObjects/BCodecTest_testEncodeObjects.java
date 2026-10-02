package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

/**
 * Verifies {@link BCodec#encode(Object)}, the {@code Object}-based entry point of the RFC 1522 "B" encoder.
 */
public class BCodecTest_testEncodeObjects {

    @Test
    void testEncodeObjects() throws Exception {
        final BCodec bcodec = new BCodec();

        // A String value is Base64 ("B") encoded and wrapped in an RFC 1522 encoded-word.
        final String plainText = "what not";
        final String encodedText = (String) bcodec.encode((Object) plainText);
        assertEquals("=?UTF-8?B?d2hhdCBub3Q=?=", encodedText, "Basic B encoding test");

        // A null value is passed through unchanged.
        final Object encodedNull = bcodec.encode((Object) null);
        assertNull(encodedNull, "Encoding a null Object should return null");

        // A non-String value (here a Double) is unsupported and must be rejected.
        assertThrows(EncoderException.class, () -> bcodec.encode(Double.valueOf(3.0d)),
                "Trying to url encode a Double object should cause an exception.");
    }
}

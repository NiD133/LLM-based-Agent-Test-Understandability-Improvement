package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

/**
 * Verifies {@link BCodec#encode(Object)}, the {@code Object}-typed entry point that
 * dispatches based on the runtime type of its argument.
 */
public class BCodecTest_testEncodeObjects {

    @Test
    void testEncodeObjects() throws Exception {
        final BCodec bcodec = new BCodec();

        // A String is B-encoded (RFC 1522 "encoded-word") using the default UTF-8 charset.
        final String plainText = "what not";
        final String encodedText = (String) bcodec.encode((Object) plainText);
        assertEquals("=?UTF-8?B?d2hhdCBub3Q=?=", encodedText, "Basic B encoding test");

        // A null input is passed through unchanged.
        assertNull(bcodec.encode((Object) null), "Encoding a null Object should return null");

        // Any non-String object is rejected because BCodec only knows how to encode strings.
        assertThrows(EncoderException.class, () -> bcodec.encode(Double.valueOf(3.0d)),
                "Trying to url encode a Double object should cause an exception.");
    }
}

package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

/**
 * Verifies {@link BCodec#encode(Object)}, the {@code Object}-typed entry point that
 * dispatches on the runtime type of its argument.
 */
public class BCodecTest_testEncodeObjects {

    @Test
    void testEncodeObjects() throws Exception {
        final BCodec bcodec = new BCodec();

        // A String is encoded as an RFC 1522 "B" (Base64) encoded-word using UTF-8.
        final String encodedString = (String) bcodec.encode((Object) "what not");
        assertEquals("=?UTF-8?B?d2hhdCBub3Q=?=", encodedString, "Basic B encoding test");

        // A null input is passed through unchanged.
        assertNull(bcodec.encode((Object) null), "Encoding a null Object should return null");

        // A non-String input cannot be encoded and is rejected.
        assertThrows(EncoderException.class, () -> bcodec.encode(Double.valueOf(3.0d)),
                "Trying to url encode a Double object should cause an exception.");
    }
}

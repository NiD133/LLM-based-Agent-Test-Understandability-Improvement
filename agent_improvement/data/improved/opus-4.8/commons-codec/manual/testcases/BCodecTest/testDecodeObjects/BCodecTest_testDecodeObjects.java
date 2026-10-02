package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link BCodec#decode(Object)}, the {@code Object}-typed entry point that
 * dispatches based on the runtime type of its argument.
 */
public class BCodecTest_testDecodeObjects {

    @Test
    void testDecodeObjects() throws Exception {
        final BCodec bcodec = new BCodec();

        // A String argument is decoded from its RFC 1522 "B" (Base64) encoded form.
        final String encodedWord = "=?UTF-8?B?d2hhdCBub3Q=?=";
        final String decoded = (String) bcodec.decode((Object) encodedWord);
        assertEquals("what not", decoded, "Basic B decoding test");

        // A null argument is passed through unchanged.
        assertNull(bcodec.decode((Object) null), "Decoding a null Object should return null");

        // A non-String argument cannot be decoded and is rejected.
        assertThrows(DecoderException.class, () -> bcodec.decode(Double.valueOf(3.0d)));
    }
}

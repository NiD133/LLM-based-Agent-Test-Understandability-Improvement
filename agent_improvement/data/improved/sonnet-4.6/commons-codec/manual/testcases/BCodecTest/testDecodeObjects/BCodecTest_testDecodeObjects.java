package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class BCodecTest_testDecodeObjects {

    @Test
    void testDecodeObjects() throws Exception {
        final BCodec bcodec = new BCodec();

        // Decoding a valid RFC-1522 B-encoded String object should return the plain text
        final String encodedWord = "=?UTF-8?B?d2hhdCBub3Q=?=";
        final String decodedText = (String) bcodec.decode((Object) encodedWord);
        assertEquals("what not", decodedText, "Basic B decoding test");

        // Decoding a null Object should return null without throwing
        final Object nullResult = bcodec.decode((Object) null);
        assertNull(nullResult, "Decoding a null Object should return null");

        // Decoding a non-String Object (e.g. Double) should throw DecoderException
        assertThrows(DecoderException.class, () -> bcodec.decode(Double.valueOf(3.0d)));
    }
}

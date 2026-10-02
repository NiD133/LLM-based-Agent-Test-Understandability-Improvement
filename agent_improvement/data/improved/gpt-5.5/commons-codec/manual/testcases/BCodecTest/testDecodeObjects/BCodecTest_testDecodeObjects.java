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

        final String encodedWord = "=?UTF-8?B?d2hhdCBub3Q=?=";
        final String decoded = (String) bcodec.decode((Object) encodedWord);
        assertEquals("what not", decoded, "Basic B decoding test");

        final Object decodedNull = bcodec.decode((Object) null);
        assertNull(decodedNull, "Decoding a null Object should return null");

        assertThrows(DecoderException.class, () -> bcodec.decode(Double.valueOf(3.0d)));
    }
}

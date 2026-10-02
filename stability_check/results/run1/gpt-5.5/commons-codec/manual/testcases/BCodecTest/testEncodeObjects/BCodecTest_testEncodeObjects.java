package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

public class BCodecTest_testEncodeObjects {

    private static final String PLAIN_TEXT = "what not";
    private static final String BASE64_ENCODED_TEXT = "=?UTF-8?B?d2hhdCBub3Q=?=";
    private static final Double UNSUPPORTED_VALUE = Double.valueOf(3.0d);

    @Test
    void testEncodeObjects() throws Exception {
        final BCodec bcodec = new BCodec();

        final String encoded = (String) bcodec.encode((Object) PLAIN_TEXT);
        assertEquals(BASE64_ENCODED_TEXT, encoded, "Basic B encoding test");

        final Object nullEncodingResult = bcodec.encode((Object) null);
        assertNull(nullEncodingResult, "Encoding a null Object should return null");

        assertThrows(EncoderException.class, () -> bcodec.encode(UNSUPPORTED_VALUE),
                "Trying to url encode a Double object should cause an exception.");
    }
}

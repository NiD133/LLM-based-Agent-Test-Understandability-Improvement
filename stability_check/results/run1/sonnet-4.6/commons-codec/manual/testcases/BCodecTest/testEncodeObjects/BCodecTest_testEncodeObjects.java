package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

public class BCodecTest_testEncodeObjects {

    @Test
    void testEncodeObjects() throws Exception {
        final BCodec bcodec = new BCodec();

        // Encoding a plain String produces an RFC-1522 Base64 "encoded-word"
        final String plainText = "what not";
        final String encoded = (String) bcodec.encode((Object) plainText);
        assertEquals("=?UTF-8?B?d2hhdCBub3Q=?=", encoded, "Basic B encoding test");

        // Encoding null should return null without throwing
        final Object nullResult = bcodec.encode((Object) null);
        assertNull(nullResult, "Encoding a null Object should return null");

        // Encoding an unsupported type (Double) must throw EncoderException
        assertThrows(EncoderException.class,
                () -> bcodec.encode(Double.valueOf(3.0d)),
                "Trying to url encode a Double object should cause an exception.");
    }
}

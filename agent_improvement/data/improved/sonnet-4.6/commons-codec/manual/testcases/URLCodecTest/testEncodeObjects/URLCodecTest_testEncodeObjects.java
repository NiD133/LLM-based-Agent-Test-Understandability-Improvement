package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

public class URLCodecTest_testEncodeObjects {

    private static final String PLAIN_TEXT = "Hello there!";
    private static final String URL_ENCODED_TEXT = "Hello+there%21";

    @Test
    void testEncodeObjects() throws Exception {
        final URLCodec urlCodec = new URLCodec();

        // Encoding a String object: spaces become '+', special chars become '%XX'
        String encodedString = (String) urlCodec.encode((Object) PLAIN_TEXT);
        assertEquals(URL_ENCODED_TEXT, encodedString, "Basic URL encoding test");

        // Encoding a byte[] object: result should match the same URL-encoded form
        final byte[] plainBytes = PLAIN_TEXT.getBytes(StandardCharsets.UTF_8);
        final byte[] encodedBytes = (byte[]) urlCodec.encode((Object) plainBytes);
        String encodedFromBytes = new String(encodedBytes);
        assertEquals(URL_ENCODED_TEXT, encodedFromBytes, "Basic URL encoding test");

        // Encoding null should return null without throwing
        final Object result = urlCodec.encode((Object) null);
        assertNull(result, "Encoding a null Object should return null");

        // Encoding an unsupported type (Double) must throw EncoderException
        assertThrows(EncoderException.class,
                () -> urlCodec.encode(Double.valueOf(3.0d)),
                "Trying to url encode a Double object should cause an exception.");
    }
}

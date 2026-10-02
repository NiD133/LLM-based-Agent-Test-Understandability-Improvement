package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class URLCodecTest_testDecodeObjects {

    private static final String URL_ENCODED_GREETING = "Hello+there%21";
    private static final String DECODED_GREETING = "Hello there!";

    private void validateState(final URLCodec urlCodec) {
        // no tests for now.
    }

    @Test
    void testDecodeObjects() throws Exception {
        final URLCodec urlCodec = new URLCodec();

        final String decodedString = (String) urlCodec.decode((Object) URL_ENCODED_GREETING);
        assertEquals(DECODED_GREETING, decodedString, "Basic URL decoding test");

        final byte[] encodedBytes = URL_ENCODED_GREETING.getBytes(StandardCharsets.UTF_8);
        final byte[] decodedBytes = (byte[]) urlCodec.decode((Object) encodedBytes);
        assertEquals(DECODED_GREETING, new String(decodedBytes), "Basic URL decoding test");

        final Object nullDecodeResult = urlCodec.decode((Object) null);
        assertNull(nullDecodeResult, "Decoding a null Object should return null");

        assertThrows(DecoderException.class, () -> urlCodec.decode(Double.valueOf(3.0d)),
                "Trying to url encode a Double object should cause an exception.");

        validateState(urlCodec);
    }
}

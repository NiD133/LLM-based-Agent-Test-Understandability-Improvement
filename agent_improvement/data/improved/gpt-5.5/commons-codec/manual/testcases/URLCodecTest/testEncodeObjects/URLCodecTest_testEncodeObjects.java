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

    private void validateState(final URLCodec urlCodec) {
        // no tests for now.
    }

    @Test
    void testEncodeObjects() throws Exception {
        final URLCodec urlCodec = new URLCodec();

        assertEncodesStringObject(urlCodec);
        assertEncodesByteArrayObject(urlCodec);
        assertNullObjectEncodesToNull(urlCodec);
        assertUnsupportedObjectTypeThrows(urlCodec);

        validateState(urlCodec);
    }

    private void assertEncodesStringObject(final URLCodec urlCodec) throws EncoderException {
        final String encoded = (String) urlCodec.encode((Object) PLAIN_TEXT);

        assertEquals(URL_ENCODED_TEXT, encoded, "Basic URL encoding test");
    }

    private void assertEncodesByteArrayObject(final URLCodec urlCodec) throws EncoderException {
        final byte[] plainBA = PLAIN_TEXT.getBytes(StandardCharsets.UTF_8);
        final byte[] encodedBA = (byte[]) urlCodec.encode((Object) plainBA);
        final String encoded = new String(encodedBA);

        assertEquals(URL_ENCODED_TEXT, encoded, "Basic URL encoding test");
    }

    private void assertNullObjectEncodesToNull(final URLCodec urlCodec) throws EncoderException {
        final Object result = urlCodec.encode((Object) null);

        assertNull(result, "Encoding a null Object should return null");
    }

    private void assertUnsupportedObjectTypeThrows(final URLCodec urlCodec) {
        assertThrows(EncoderException.class, () -> urlCodec.encode(Double.valueOf(3.0d)),
                "Trying to url encode a Double object should cause an exception.");
    }
}

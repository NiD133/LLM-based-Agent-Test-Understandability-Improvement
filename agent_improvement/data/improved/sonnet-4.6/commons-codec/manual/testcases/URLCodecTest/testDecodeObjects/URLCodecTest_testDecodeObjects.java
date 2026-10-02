package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class URLCodecTest_testDecodeObjects {

    private static final String URL_ENCODED_HELLO = "Hello+there%21";
    private static final String DECODED_HELLO = "Hello there!";

    @Test
    void testDecodeObject_string_decodesCorrectly() throws Exception {
        final URLCodec urlCodec = new URLCodec();

        final String decoded = (String) urlCodec.decode((Object) URL_ENCODED_HELLO);

        assertEquals(DECODED_HELLO, decoded, "Basic URL decoding test");
    }

    @Test
    void testDecodeObject_byteArray_decodesCorrectly() throws Exception {
        final URLCodec urlCodec = new URLCodec();
        final byte[] plainBA = URL_ENCODED_HELLO.getBytes(StandardCharsets.UTF_8);

        final byte[] decodedBA = (byte[]) urlCodec.decode((Object) plainBA);
        final String decoded = new String(decodedBA);

        assertEquals(DECODED_HELLO, decoded, "Basic URL decoding test");
    }

    @Test
    void testDecodeObject_null_returnsNull() throws Exception {
        final URLCodec urlCodec = new URLCodec();

        final Object result = urlCodec.decode((Object) null);

        assertNull(result, "Decoding a null Object should return null");
    }

    @Test
    void testDecodeObject_unsupportedType_throwsDecoderException() {
        final URLCodec urlCodec = new URLCodec();

        assertThrows(DecoderException.class,
            () -> urlCodec.decode(Double.valueOf(3.0d)),
            "Trying to url encode a Double object should cause an exception.");
    }
}

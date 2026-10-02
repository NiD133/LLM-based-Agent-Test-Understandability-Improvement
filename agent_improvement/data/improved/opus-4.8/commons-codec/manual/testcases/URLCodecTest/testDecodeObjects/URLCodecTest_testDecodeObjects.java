package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link URLCodec#decode(Object)}, the type-dispatching overload that accepts an
 * arbitrary {@code Object} and routes it to the {@code String} or {@code byte[]} decoder.
 */
public class URLCodecTest_testDecodeObjects {

    /** URL-encoded form of the text "Hello there!" ('+' for space, "%21" for '!'). */
    private static final String URL_ENCODED_TEXT = "Hello+there%21";

    /** The plain text that {@link #URL_ENCODED_TEXT} decodes back to. */
    private static final String DECODED_TEXT = "Hello there!";

    @Test
    void decodeObjectAcceptsString() throws Exception {
        final URLCodec urlCodec = new URLCodec();

        final String decoded = (String) urlCodec.decode((Object) URL_ENCODED_TEXT);

        assertEquals(DECODED_TEXT, decoded, "Decoding a String Object should URL-decode it");
    }

    @Test
    void decodeObjectAcceptsByteArray() throws Exception {
        final URLCodec urlCodec = new URLCodec();
        final byte[] encodedBytes = URL_ENCODED_TEXT.getBytes(StandardCharsets.UTF_8);

        final byte[] decodedBytes = (byte[]) urlCodec.decode((Object) encodedBytes);

        assertEquals(DECODED_TEXT, new String(decodedBytes), "Decoding a byte[] Object should URL-decode it");
    }

    @Test
    void decodeObjectReturnsNullForNull() throws Exception {
        final URLCodec urlCodec = new URLCodec();

        final Object result = urlCodec.decode((Object) null);

        assertNull(result, "Decoding a null Object should return null");
    }

    @Test
    void decodeObjectRejectsUnsupportedType() {
        final URLCodec urlCodec = new URLCodec();

        assertThrows(DecoderException.class, () -> urlCodec.decode(Double.valueOf(3.0d)),
                "Decoding an unsupported type (Double) should throw DecoderException");
    }
}

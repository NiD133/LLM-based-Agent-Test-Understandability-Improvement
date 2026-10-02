package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link URLCodec#encode(Object)}, which dispatches on the runtime type of
 * its argument: {@code String} and {@code byte[]} are URL encoded, {@code null}
 * passes through, and any other type is rejected.
 */
public class URLCodecTest_testEncodeObjects {

    /** Plain text containing characters that must be URL escaped: a space and a '!'. */
    private static final String PLAIN_TEXT = "Hello there!";

    /** {@link #PLAIN_TEXT} after URL encoding: space becomes '+' and '!' becomes "%21". */
    private static final String URL_ENCODED_TEXT = "Hello+there%21";

    private final URLCodec urlCodec = new URLCodec();

    @Test
    void encodeObjectShouldUrlEncodeString() throws EncoderException {
        final Object encoded = urlCodec.encode((Object) PLAIN_TEXT);

        assertEquals(URL_ENCODED_TEXT, (String) encoded, "A String argument should be URL encoded");
    }

    @Test
    void encodeObjectShouldUrlEncodeByteArray() throws EncoderException {
        final byte[] plainBytes = PLAIN_TEXT.getBytes(StandardCharsets.UTF_8);

        final byte[] encodedBytes = (byte[]) urlCodec.encode((Object) plainBytes);

        assertEquals(URL_ENCODED_TEXT, new String(encodedBytes), "A byte[] argument should be URL encoded");
    }

    @Test
    void encodeObjectShouldReturnNullForNull() throws EncoderException {
        assertNull(urlCodec.encode((Object) null), "Encoding a null Object should return null");
    }

    @Test
    void encodeObjectShouldRejectUnsupportedType() {
        assertThrows(EncoderException.class, () -> urlCodec.encode(Double.valueOf(3.0d)),
                "Encoding an unsupported type (Double) should throw EncoderException");
    }
}

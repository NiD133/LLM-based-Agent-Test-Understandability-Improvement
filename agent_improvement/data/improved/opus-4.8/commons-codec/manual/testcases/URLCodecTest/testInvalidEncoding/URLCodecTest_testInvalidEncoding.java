package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

/**
 * Verifies that a {@link URLCodec} configured with an unsupported charset name
 * fails fast when asked to encode or decode a String.
 */
public class URLCodecTest_testInvalidEncoding {

    /** A charset name that does not correspond to any real charset. */
    private static final String UNSUPPORTED_CHARSET = "NONSENSE";

    @Test
    void encodeAndDecodeThrowWhenCharsetIsUnsupported() {
        final URLCodec urlCodec = new URLCodec(UNSUPPORTED_CHARSET);
        final String input = "Hello there!";

        assertThrows(EncoderException.class, () -> urlCodec.encode(input),
                "encode should fail because the configured charset is unsupported");
        assertThrows(DecoderException.class, () -> urlCodec.decode(input),
                "decode should fail because the configured charset is unsupported");
    }
}

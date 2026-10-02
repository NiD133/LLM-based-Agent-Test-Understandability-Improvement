package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

/**
 * Verifies that a {@link URLCodec} configured with an unsupported charset name
 * fails fast when asked to encode or decode a string.
 */
public class URLCodecTest_testInvalidEncoding {

    /** A charset name that no JVM recognizes, used to force encoding/decoding failures. */
    private static final String BOGUS_CHARSET = "NONSENSE";

    @Test
    void testInvalidEncoding() {
        final URLCodec urlCodec = new URLCodec(BOGUS_CHARSET);
        final String input = "Hello there!";

        assertThrows(EncoderException.class, () -> urlCodec.encode(input),
                "Encoding must fail because the configured charset is invalid");
        assertThrows(DecoderException.class, () -> urlCodec.decode(input),
                "Decoding must fail because the configured charset is invalid");
    }
}

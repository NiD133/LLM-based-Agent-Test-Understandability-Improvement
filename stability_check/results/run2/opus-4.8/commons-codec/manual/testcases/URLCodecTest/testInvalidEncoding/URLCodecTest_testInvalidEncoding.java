package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link URLCodec} rejects an unsupported charset name.
 */
public class URLCodecTest_testInvalidEncoding {

    /** A charset name that is not a valid, supported charset. */
    private static final String UNSUPPORTED_CHARSET = "NONSENSE";

    @Test
    void testInvalidEncoding() {
        final URLCodec urlCodec = new URLCodec(UNSUPPORTED_CHARSET);
        final String input = "Hello there!";

        // Encoding must fail because the configured charset is bogus.
        assertThrows(EncoderException.class, () -> urlCodec.encode(input),
                "Encoding with an unsupported charset should throw");

        // Decoding must fail for the same reason.
        assertThrows(DecoderException.class, () -> urlCodec.decode(input),
                "Decoding with an unsupported charset should throw");
    }
}

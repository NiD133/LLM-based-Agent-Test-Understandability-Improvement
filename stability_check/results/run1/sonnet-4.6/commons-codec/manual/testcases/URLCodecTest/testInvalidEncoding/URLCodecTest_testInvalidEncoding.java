package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

/**
 * Tests that URLCodec throws the appropriate exceptions when initialized
 * with an unsupported (bogus) charset encoding name.
 */
public class URLCodecTest_testInvalidEncoding {

    private static final String BOGUS_CHARSET = "NONSENSE";
    private static final String PLAIN_TEXT = "Hello there!";

    @Test
    void testInvalidEncoding() {
        final URLCodec urlCodec = new URLCodec(BOGUS_CHARSET);

        assertThrows(EncoderException.class, () -> urlCodec.encode(PLAIN_TEXT),
                "encode() should throw EncoderException when charset is invalid");
        assertThrows(DecoderException.class, () -> urlCodec.decode(PLAIN_TEXT),
                "decode() should throw DecoderException when charset is invalid");
    }
}

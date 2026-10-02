package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

public class URLCodecTest_testInvalidEncoding {

    private static final String INVALID_CHARSET = "NONSENSE";
    private static final String PLAIN_TEXT = "Hello there!";
    private static final String INVALID_CHARSET_MESSAGE = "We set the encoding to a bogus NONSENSE value";

    private void validateState(final URLCodec urlCodec) {
        // no tests for now.
    }

    @Test
    void testInvalidEncoding() {
        final URLCodec urlCodec = new URLCodec(INVALID_CHARSET);

        assertThrows(EncoderException.class, () -> urlCodec.encode(PLAIN_TEXT), INVALID_CHARSET_MESSAGE);
        assertThrows(DecoderException.class, () -> urlCodec.decode(PLAIN_TEXT), INVALID_CHARSET_MESSAGE);

        validateState(urlCodec);
    }
}

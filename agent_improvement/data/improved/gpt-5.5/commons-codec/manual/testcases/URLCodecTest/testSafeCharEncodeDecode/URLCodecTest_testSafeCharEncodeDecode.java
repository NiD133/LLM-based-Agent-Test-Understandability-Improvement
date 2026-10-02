package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class URLCodecTest_testSafeCharEncodeDecode {

    private static final String SAFE_URL_CHARACTERS = "abc123_-.*";
    private static final String ENCODING_MESSAGE = "Safe chars URL encoding test";
    private static final String DECODING_MESSAGE = "Safe chars URL decoding test";

    private void validateState(final URLCodec urlCodec) {
        // No state assertions are required for this focused behavior check.
    }

    @Test
    void testSafeCharEncodeDecode() throws Exception {
        final URLCodec urlCodec = new URLCodec();

        final String encoded = urlCodec.encode(SAFE_URL_CHARACTERS);

        assertEquals(SAFE_URL_CHARACTERS, encoded, ENCODING_MESSAGE);
        assertEquals(SAFE_URL_CHARACTERS, urlCodec.decode(encoded), DECODING_MESSAGE);
        validateState(urlCodec);
    }
}

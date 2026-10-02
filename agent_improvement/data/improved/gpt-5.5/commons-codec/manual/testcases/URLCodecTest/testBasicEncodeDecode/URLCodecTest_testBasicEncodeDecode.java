package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class URLCodecTest_testBasicEncodeDecode {

    private static final String PLAIN_TEXT = "Hello there!";
    private static final String URL_ENCODED_TEXT = "Hello+there%21";

    private void validateState(final URLCodec urlCodec) {
        // no tests for now.
    }

    @Test
    void testBasicEncodeDecode() throws Exception {
        final URLCodec urlCodec = new URLCodec();

        assertEquals(URL_ENCODED_TEXT, urlCodec.encode(PLAIN_TEXT), "Basic URL encoding test");
        assertEquals(PLAIN_TEXT, urlCodec.decode(URL_ENCODED_TEXT), "Basic URL decoding test");
        validateState(urlCodec);
    }
}

package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class URLCodecTest_testDefaultEncoding {

    private static final String DEFAULT_CHARSET = "UnicodeBig";
    private static final String PLAIN_TEXT = "Hello there!";

    @Test
    void testDefaultEncoding() throws Exception {
        final URLCodec urlCodec = new URLCodec(DEFAULT_CHARSET);

        // Preserve the original warm-up call before comparing explicit and default charset encoding.
        urlCodec.encode(PLAIN_TEXT);

        final String explicitlyEncoded = urlCodec.encode(PLAIN_TEXT, DEFAULT_CHARSET);
        final String encodedWithDefaultCharset = urlCodec.encode(PLAIN_TEXT);

        assertEquals(explicitlyEncoded, encodedWithDefaultCharset);
    }
}

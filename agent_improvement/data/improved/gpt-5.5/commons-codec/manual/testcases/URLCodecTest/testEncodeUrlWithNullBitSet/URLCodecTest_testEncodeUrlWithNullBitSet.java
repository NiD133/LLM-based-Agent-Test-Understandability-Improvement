package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class URLCodecTest_testEncodeUrlWithNullBitSet {

    private void validateState(final URLCodec urlCodec) {
        // no tests for now.
    }

    @Test
    void testEncodeUrlWithNullBitSet() throws Exception {
        final URLCodec urlCodec = new URLCodec();
        final String plainText = "Hello there!";

        final byte[] encodedBytes = URLCodec.encodeUrl(null, plainText.getBytes(StandardCharsets.UTF_8));
        final String encodedText = new String(encodedBytes);

        assertEquals("Hello+there%21", encodedText, "Basic URL encoding test");
        assertEquals(plainText, urlCodec.decode(encodedText), "Basic URL decoding test");
        validateState(urlCodec);
    }
}

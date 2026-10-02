package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class PercentCodecTest_testBasicEncodeDecode {

    @Test
    void testBasicEncodeDecode() throws Exception {
        final PercentCodec percentCodec = new PercentCodec();
        final String originalText = "abcdABCD";

        final byte[] encodedBytes = percentCodec.encode(originalText.getBytes(StandardCharsets.UTF_8));
        final String encodedText = new String(encodedBytes, StandardCharsets.UTF_8);

        final byte[] decodedBytes = percentCodec.decode(encodedBytes);
        final String decodedText = new String(decodedBytes, StandardCharsets.UTF_8);

        assertEquals(originalText, encodedText, "Basic PercentCodec encoding test");
        assertEquals(originalText, decodedText, "Basic PercentCodec decoding test");
    }
}

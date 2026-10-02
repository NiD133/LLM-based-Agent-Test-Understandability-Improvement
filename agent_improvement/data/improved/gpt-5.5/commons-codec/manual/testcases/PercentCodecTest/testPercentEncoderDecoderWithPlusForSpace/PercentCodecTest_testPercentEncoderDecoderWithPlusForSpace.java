package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class PercentCodecTest_testPercentEncoderDecoderWithPlusForSpace {

    private static final String TEXT_WITH_SPACES = "a b c d";
    private static final String TEXT_WITH_PLUS_SEPARATORS = "a+b+c+d";

    @Test
    void testPercentEncoderDecoderWithPlusForSpace() throws Exception {
        final PercentCodec percentCodec = new PercentCodec(null, true);

        final byte[] encodedBytes = percentCodec.encode(TEXT_WITH_SPACES.getBytes(StandardCharsets.UTF_8));
        final String encodedText = new String(encodedBytes, StandardCharsets.UTF_8);
        assertEquals(TEXT_WITH_PLUS_SEPARATORS, encodedText, "PercentCodec plus for space encoding test");

        final byte[] decodedBytes = percentCodec.decode(encodedBytes);
        final String decodedText = new String(decodedBytes, StandardCharsets.UTF_8);
        assertEquals(decodedText, TEXT_WITH_SPACES, "PercentCodec plus for space decoding test");
    }
}

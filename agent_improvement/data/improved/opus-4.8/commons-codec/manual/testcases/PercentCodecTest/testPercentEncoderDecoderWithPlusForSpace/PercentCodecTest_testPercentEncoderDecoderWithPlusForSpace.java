package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link PercentCodec}, when configured with {@code plusForSpace = true},
 * encodes each space character as a '+' and decodes it back to a space.
 */
public class PercentCodecTest_testPercentEncoderDecoderWithPlusForSpace {

    @Test
    void testPercentEncoderDecoderWithPlusForSpace() throws Exception {
        // Arrange: a codec that maps spaces to '+', and input text containing spaces.
        final String originalText = "a b c d";
        final boolean plusForSpace = true;
        final PercentCodec percentCodec = new PercentCodec(null, plusForSpace);

        // Act: encode the input bytes, then decode the result back.
        final byte[] encodedBytes = percentCodec.encode(originalText.getBytes(StandardCharsets.UTF_8));
        final String encodedText = new String(encodedBytes, StandardCharsets.UTF_8);

        final byte[] decodedBytes = percentCodec.decode(encodedBytes);
        final String decodedText = new String(decodedBytes, StandardCharsets.UTF_8);

        // Assert: every space became a '+' when encoding, and decoding restores the original text.
        assertEquals("a+b+c+d", encodedText, "PercentCodec plus for space encoding test");
        assertEquals(originalText, decodedText, "PercentCodec plus for space decoding test");
    }
}

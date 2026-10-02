package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

public class PercentCodecTest_testPercentEncoderDecoderWithPlusForSpace {

    @Test
    void testPercentEncoderDecoderWithPlusForSpace() throws Exception {
        // Arrange: a codec that replaces spaces with '+' (application/x-www-form-urlencoded style)
        final String input = "a b c d";
        final PercentCodec percentCodec = new PercentCodec(null, true);

        // Act: encode the input
        final byte[] encodedBytes = percentCodec.encode(input.getBytes(StandardCharsets.UTF_8));
        final String encodedString = new String(encodedBytes, StandardCharsets.UTF_8);

        // Assert: spaces are replaced by '+'
        assertEquals("a+b+c+d", encodedString, "PercentCodec plus for space encoding test");

        // Act: decode back to the original
        final byte[] decodedBytes = percentCodec.decode(encodedBytes);

        // Assert: round-trip produces the original input
        assertEquals(input, new String(decodedBytes, StandardCharsets.UTF_8), "PercentCodec plus for space decoding test");
    }
}

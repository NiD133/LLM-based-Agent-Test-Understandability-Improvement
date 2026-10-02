package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class PercentCodecTest_testPercentEncoderDecoderWithNullOrEmptyInput {

    @Test
    void testPercentEncoderDecoderWithNullOrEmptyInput() throws Exception {
        final PercentCodec percentCodec = new PercentCodec(null, true);

        assertNull(percentCodec.encode(null), "Null input value encoding test");
        assertNull(percentCodec.decode(null), "Null input value decoding test");

        final byte[] emptyInput = "".getBytes(StandardCharsets.UTF_8);
        final byte[] encodedEmptyInput = percentCodec.encode(emptyInput);
        final byte[] decodedEmptyInput = percentCodec.decode(emptyInput);

        assertEquals(encodedEmptyInput, emptyInput, "Empty input value encoding test");
        assertArrayEquals(decodedEmptyInput, emptyInput, "Empty input value decoding test");
    }
}

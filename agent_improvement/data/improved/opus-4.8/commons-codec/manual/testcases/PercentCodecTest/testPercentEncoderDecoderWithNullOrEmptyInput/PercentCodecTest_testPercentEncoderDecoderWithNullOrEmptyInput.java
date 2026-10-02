package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link PercentCodec} handles the two boundary inputs - {@code null} and an
 * empty byte array - gracefully, for both encoding and decoding.
 */
public class PercentCodecTest_testPercentEncoderDecoderWithNullOrEmptyInput {

    @Test
    void testPercentEncoderDecoderWithNullOrEmptyInput() throws Exception {
        // A codec with no extra "always encode" characters; spaces are encoded as '+'.
        final PercentCodec percentCodec = new PercentCodec(null, true);

        // Null input must be passed through as null, untouched.
        assertNull(percentCodec.encode((byte[]) null), "Encoding a null input should return null");
        assertNull(percentCodec.decode((byte[]) null), "Decoding a null input should return null");

        // Empty input must be returned unchanged (the same empty byte array).
        final byte[] emptyInput = "".getBytes(StandardCharsets.UTF_8);
        assertEquals(emptyInput, percentCodec.encode(emptyInput), "Encoding empty input should return the same empty array");
        assertArrayEquals(emptyInput, percentCodec.decode(emptyInput), "Decoding empty input should return an empty array");
    }
}

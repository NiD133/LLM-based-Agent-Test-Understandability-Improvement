package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that PercentCodec handles null and empty byte-array inputs gracefully
 * without throwing exceptions and without altering the data.
 *
 * Codec configuration: no always-encode characters (null), plusForSpace=true.
 */
public class PercentCodecTest_testPercentEncoderDecoderWithNullOrEmptyInput {

    // Codec under test: no extra always-encode chars, space encoded as '+'
    private PercentCodec codec;

    @BeforeEach
    void setUp() {
        codec = new PercentCodec(null, true);
    }

    @Test
    void encode_withNullInput_returnsNull() throws EncoderException {
        assertNull(codec.encode((byte[]) null), "Encoding null should return null");
    }

    @Test
    void decode_withNullInput_returnsNull() throws DecoderException {
        assertNull(codec.decode((byte[]) null), "Decoding null should return null");
    }

    @Test
    void encode_withEmptyByteArray_returnsTheSameEmptyArray() throws EncoderException {
        byte[] emptyInput = "".getBytes(StandardCharsets.UTF_8);
        assertEquals(codec.encode(emptyInput), emptyInput,
                "Encoding an empty byte array should return the identical array reference");
    }

    @Test
    void decode_withEmptyByteArray_returnsEmptyByteArray() throws DecoderException {
        byte[] emptyInput = "".getBytes(StandardCharsets.UTF_8);
        assertArrayEquals(codec.decode(emptyInput), emptyInput,
                "Decoding an empty byte array should produce an empty byte array");
    }
}

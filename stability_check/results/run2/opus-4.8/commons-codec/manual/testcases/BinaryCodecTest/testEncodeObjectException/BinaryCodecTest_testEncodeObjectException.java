package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies the exception contract of {@link BinaryCodec#encode(Object)}.
 */
public class BinaryCodecTest_testEncodeObjectException {

    /** The binary codec under test. */
    private BinaryCodec codec;

    @BeforeEach
    void setUp() {
        codec = new BinaryCodec();
    }

    /**
     * {@code encode(Object)} only accepts a {@code byte[]}; any other argument
     * type (here a {@link String}) must be rejected with an {@link EncoderException}.
     */
    @Test
    void testEncodeObjectException() {
        assertThrows(EncoderException.class, () -> codec.encode(""));
    }
}

package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link BinaryCodec#decode(Object)} when given an unsupported argument type.
 */
public class BinaryCodecTest_testDecodeObjectException {

    /** The binary codec under test. */
    private BinaryCodec codec;

    @BeforeEach
    void setUp() {
        codec = new BinaryCodec();
    }

    /**
     * Decoding an argument that is neither a byte[], char[] nor String
     * must raise a {@link DecoderException}.
     */
    @Test
    void testDecodeObjectException() {
        final Object unsupportedArgument = new Object();

        assertThrows(DecoderException.class, () -> codec.decode(unsupportedArgument));
    }
}

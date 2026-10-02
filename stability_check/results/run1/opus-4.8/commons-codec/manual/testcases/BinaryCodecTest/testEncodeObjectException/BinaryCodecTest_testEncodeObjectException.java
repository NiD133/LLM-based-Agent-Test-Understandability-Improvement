package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies the exception behaviour of {@link BinaryCodec#encode(Object)}.
 */
public class BinaryCodecTest_testEncodeObjectException {

    /** The binary codec under test. */
    private BinaryCodec instance;

    @BeforeEach
    void setUp() {
        this.instance = new BinaryCodec();
    }

    /**
     * {@code encode(Object)} only accepts a {@code byte[]}. Passing any other
     * type, such as a String, must raise an {@link EncoderException}.
     */
    @Test
    void encodeRejectsNonByteArrayArgument() {
        assertThrows(EncoderException.class, () -> instance.encode(""));
    }
}

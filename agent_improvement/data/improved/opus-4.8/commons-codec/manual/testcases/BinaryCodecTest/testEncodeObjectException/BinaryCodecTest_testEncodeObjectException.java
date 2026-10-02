package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link BinaryCodec#encode(Object)} for its error-handling contract.
 */
public class BinaryCodecTest_testEncodeObjectException {

    /** The codec under test, freshly created before each test. */
    private BinaryCodec codec;

    @BeforeEach
    void setUp() {
        codec = new BinaryCodec();
    }

    /**
     * {@code encode(Object)} only accepts a {@code byte[]}. Passing any other
     * type, such as a String, must raise an {@link EncoderException}.
     */
    @Test
    void encodeRejectsNonByteArrayArgument() {
        final String notAByteArray = "";

        assertThrows(EncoderException.class, () -> codec.encode(notAByteArray));
    }
}

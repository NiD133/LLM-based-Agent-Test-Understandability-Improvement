package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Base58#decode(Object)} rejects arguments whose type it
 * cannot handle.
 */
public class Base58Test_testObjectDecodeWithInvalidParameter {

    /**
     * The generic {@code decode(Object)} entry point only supports byte arrays
     * and Strings. Passing any other type, such as an {@link Integer}, must fail
     * with a {@link DecoderException}.
     */
    @Test
    void testObjectDecodeWithInvalidParameter() {
        final Object unsupportedInput = Integer.valueOf(5);

        assertThrows(DecoderException.class, () -> new Base58().decode(unsupportedInput));
    }
}

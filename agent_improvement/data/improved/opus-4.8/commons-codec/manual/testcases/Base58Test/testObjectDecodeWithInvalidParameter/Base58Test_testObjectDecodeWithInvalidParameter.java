package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Base58#decode(Object)} rejects input that is not a byte array.
 */
public class Base58Test_testObjectDecodeWithInvalidParameter {

    /**
     * The {@code decode(Object)} contract only accepts {@code byte[]} input.
     * Passing any other type (here an {@link Integer}) must fail with a
     * {@link DecoderException} rather than being silently decoded.
     */
    @Test
    void testObjectDecodeWithInvalidParameter() {
        final Object nonByteArrayInput = Integer.valueOf(5);

        assertThrows(DecoderException.class, () -> new Base58().decode(nonByteArrayInput));
    }
}

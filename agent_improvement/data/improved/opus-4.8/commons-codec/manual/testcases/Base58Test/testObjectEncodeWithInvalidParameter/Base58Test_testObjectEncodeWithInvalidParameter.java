package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Base58}'s generic {@code Object} encode entry point rejects
 * unsupported argument types.
 */
public class Base58Test_testObjectEncodeWithInvalidParameter {

    /**
     * The {@code encode(Object)} contract only accepts {@code byte[]} input. Passing a
     * {@link String} is an invalid parameter and must raise an {@link EncoderException}.
     */
    @Test
    void testObjectEncodeWithInvalidParameter() {
        final Base58 base58 = new Base58();
        final String unsupportedInput = "Yadayadayada";

        assertThrows(EncoderException.class, () -> base58.encode(unsupportedInput));
    }
}

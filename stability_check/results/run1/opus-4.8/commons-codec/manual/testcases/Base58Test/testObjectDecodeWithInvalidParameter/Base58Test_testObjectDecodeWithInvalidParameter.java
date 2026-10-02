package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Base58#decode(Object)} rejects inputs that are not byte arrays.
 */
public class Base58Test_testObjectDecodeWithInvalidParameter {

    @Test
    void testObjectDecodeWithInvalidParameter() {
        final Base58 base58 = new Base58();

        // An Integer is not a valid decode input (only byte[] is accepted),
        // so decoding it must fail with a DecoderException.
        assertThrows(DecoderException.class, () -> base58.decode(Integer.valueOf(5)));
    }
}

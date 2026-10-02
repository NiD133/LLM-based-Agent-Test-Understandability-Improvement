package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class Base16Test_testObjectDecodeWithInvalidParameter {

    /**
     * Decoding an object that is not a {@code byte[]} or {@code String}
     * (here an {@link Integer}) is unsupported and must raise a
     * {@link DecoderException}.
     */
    @Test
    void testObjectDecodeWithInvalidParameter() {
        final Base16 base16 = new Base16();
        final Object unsupportedInput = Integer.valueOf(5);

        assertThrows(DecoderException.class, () -> base16.decode(unsupportedInput));
    }
}

package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Base16#encode(Object)} rejects an argument that is not a
 * {@code byte[]}.
 */
public class Base16Test_testObjectEncodeWithInvalidParameter {

    /**
     * The {@code Object}-based encode contract only accepts {@code byte[]} input;
     * passing a {@code String} must raise an {@link EncoderException}.
     */
    @Test
    void testObjectEncodeWithInvalidParameter() {
        final Base16 base16 = new Base16();
        final String nonByteArrayInput = "Yadayadayada";

        assertThrows(EncoderException.class, () -> base16.encode(nonByteArrayInput));
    }
}

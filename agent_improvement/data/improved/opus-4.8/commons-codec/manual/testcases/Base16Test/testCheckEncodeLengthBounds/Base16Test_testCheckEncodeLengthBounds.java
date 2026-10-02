package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Base16#encode(byte[], int, int)} rejects a length that is
 * large enough to overflow the size of the encoded output.
 */
public class Base16Test_testCheckEncodeLengthBounds {

    /**
     * Each input byte is encoded as two Base16 characters, so the encoder computes
     * {@code length * 2}. A length of {@code 1 << 30} makes that product overflow a
     * positive {@code int}, which the encoder must reject with an
     * {@link IllegalArgumentException}.
     */
    @Test
    void testCheckEncodeLengthBounds() {
        final Base16 base16 = new Base16();
        final int overflowingLength = 1 << 30;

        assertThrows(IllegalArgumentException.class,
                () -> base16.encode(new byte[10], 0, overflowingLength));
    }
}

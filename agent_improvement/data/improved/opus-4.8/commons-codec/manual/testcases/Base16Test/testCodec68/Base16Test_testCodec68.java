package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class Base16Test_testCodec68 {

    /**
     * CODEC-68: decoding input that contains characters outside the Base16
     * alphabet must fail. Here the trailing {@code 0x9c} byte is not a valid
     * Base16 character, so {@link Base16#decode(byte[])} is expected to throw.
     */
    @Test
    void testCodec68() {
        final byte[] invalidBase16 = { 'n', 'H', '=', '=', (byte) 0x9c };
        final Base16 base16 = new Base16();

        assertThrows(RuntimeException.class, () -> base16.decode(invalidBase16));
    }
}

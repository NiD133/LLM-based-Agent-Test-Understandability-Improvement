package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class Base16Test_testCodec68 {

    /**
     * Decoding input that contains a byte outside the Base16 alphabet must fail.
     *
     * <p>Here the trailing byte {@code 0x9c} is not a valid Base16 character, so
     * {@link Base16#decode(byte[])} is expected to throw a {@link RuntimeException}
     * (an {@link IllegalArgumentException} in practice).</p>
     */
    @Test
    void testCodec68() {
        final byte[] inputWithInvalidByte = { 'n', 'H', '=', '=', (byte) 0x9c };
        final Base16 base16 = new Base16();

        assertThrows(RuntimeException.class, () -> base16.decode(inputWithInvalidByte));
    }
}

package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class Base16Test_testCodec68 {

    /**
     * Verifies that Base16.decode() throws a RuntimeException when the input contains
     * characters that are not valid Base16 alphabet characters (e.g., '=', 'n', 0x9c).
     */
    @Test
    void testCodec68() {
        // Input mixes valid hex ('H') with invalid Base16 chars ('n', '=', 0x9c)
        final byte[] invalidBase16Input = { 'n', 'H', '=', '=', (byte) 0x9c };
        final Base16 b16 = new Base16();
        assertThrows(RuntimeException.class, () -> b16.decode(invalidBase16Input));
    }
}

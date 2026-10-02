package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class Base16Test_testCodec68 {

    /**
     * Base16.decode(byte[]) rejects bytes outside the Base16 alphabet.
     */
    @Test
    void testCodec68() {
        final byte[] invalidBase16Bytes = { 'n', 'H', '=', '=', (byte) 0x9c };
        final Base16 base16 = new Base16();

        assertThrows(RuntimeException.class, () -> base16.decode(invalidBase16Bytes));
    }
}

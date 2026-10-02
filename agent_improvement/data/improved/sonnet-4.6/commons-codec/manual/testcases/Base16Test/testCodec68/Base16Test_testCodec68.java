package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class Base16Test_testCodec68 {

    /**
     * Verifies that decoding a byte array containing invalid Base16 characters
     * (lowercase 'n', padding '=', and a high-byte value 0x9c) throws a
     * RuntimeException, since none of these are valid uppercase hex digits.
     */
    @Test
    void testCodec68() {
        final byte[] invalidBase16Bytes = { 'n', 'H', '=', '=', (byte) 0x9c };
        final Base16 b16 = new Base16();
        assertThrows(RuntimeException.class, () -> b16.decode(invalidBase16Bytes));
    }
}

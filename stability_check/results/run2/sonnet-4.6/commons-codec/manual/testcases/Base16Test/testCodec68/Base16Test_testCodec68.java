package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class Base16Test_testCodec68 {

    /**
     * Base16.decode() must throw a RuntimeException when the input contains bytes
     * that are not valid Base16 hex characters (e.g. 'n', '=', and 0x9c).
     */
    @Test
    void testCodec68() {
        // 'n', 'H', '=', '=', and 0x9c are all outside the Base16 alphabet (0-9, A-F)
        final byte[] invalidBase16Bytes = { 'n', 'H', '=', '=', (byte) 0x9c };
        final Base16 b16 = new Base16();
        assertThrows(RuntimeException.class, () -> b16.decode(invalidBase16Bytes));
    }
}

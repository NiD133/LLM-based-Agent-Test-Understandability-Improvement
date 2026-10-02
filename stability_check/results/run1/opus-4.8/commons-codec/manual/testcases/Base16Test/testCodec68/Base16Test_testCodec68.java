package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class Base16Test_testCodec68 {

    /**
     * Verifies that decoding a byte array containing characters outside the Base16
     * alphabet (CODEC-68) fails by throwing a {@link RuntimeException}.
     */
    @Test
    void testCodec68() {
        // 'n' and 0x9c are not valid Base16 alphabet characters.
        final byte[] invalidBase16Bytes = { 'n', 'H', '=', '=', (byte) 0x9c };
        final Base16 base16 = new Base16();

        assertThrows(RuntimeException.class, () -> base16.decode(invalidBase16Bytes));
    }
}

package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class Base16Test_testCheckEncodeLengthBounds {

    private static final int INPUT_LENGTH_THAT_OVERFLOWS_ENCODED_SIZE = 1 << 30;

    @Test
    void testCheckEncodeLengthBounds() {
        final Base16 base16 = new Base16();
        final byte[] input = new byte[10];

        assertThrows(IllegalArgumentException.class, () -> base16.encode(input, 0, INPUT_LENGTH_THAT_OVERFLOWS_ENCODED_SIZE));
    }
}

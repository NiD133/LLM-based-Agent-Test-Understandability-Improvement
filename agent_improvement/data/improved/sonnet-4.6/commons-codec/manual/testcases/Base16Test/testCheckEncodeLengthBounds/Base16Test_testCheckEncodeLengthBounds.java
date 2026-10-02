package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class Base16Test_testCheckEncodeLengthBounds {

    // Base16 encodes each byte as 2 hex characters.  Multiplying 1<<30 by 2
    // overflows a signed int to a negative value, which the encoder must detect
    // and reject rather than allocating a nonsensical buffer.
    private static final int LENGTH_CAUSING_ENCODED_SIZE_OVERFLOW = 1 << 30;

    @Test
    void testCheckEncodeLengthBounds() {
        final Base16 base16 = new Base16();
        assertThrows(
            IllegalArgumentException.class,
            () -> base16.encode(new byte[10], 0, LENGTH_CAUSING_ENCODED_SIZE_OVERFLOW),
            "encode() must reject a length whose encoded size overflows int"
        );
    }
}

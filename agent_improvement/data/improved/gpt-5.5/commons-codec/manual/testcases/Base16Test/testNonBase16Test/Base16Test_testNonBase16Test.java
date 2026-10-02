package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class Base16Test_testNonBase16Test {

    private static final byte[] INVALID_BASE16_CHARS = { '/', ':', '@', 'G', '%', '`', 'g' };

    @Test
    void testNonBase16Test() {
        final byte[] encoded = new byte[1];

        for (final byte invalidEncodedChar : INVALID_BASE16_CHARS) {
            encoded[0] = invalidEncodedChar;

            assertThrows(IllegalArgumentException.class, () -> new Base16().decode(encoded), "Invalid Base16 char: " + (char) invalidEncodedChar);
        }
    }
}

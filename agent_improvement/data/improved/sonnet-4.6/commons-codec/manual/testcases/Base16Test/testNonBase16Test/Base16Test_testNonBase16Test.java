package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class Base16Test_testNonBase16Test {

    @Test
    void testNonBase16Test() {
        // Characters that lie outside the Base16 alphabet (0-9, A-F):
        //   '/' (47) and ':' (58) bracket the digit range 0-9 (48-57)
        //   '@' (64) and 'G' (71) bracket the uppercase hex letter range A-F (65-70)
        //   '%' (37) is an unrelated special character
        //   '`' (96) and 'g' (103) bracket the lowercase letter range a-f (97-102),
        //       which is invalid for the default upper-case Base16 codec
        final byte[] charsOutsideBase16Alphabet = { '/', ':', '@', 'G', '%', '`', 'g' };

        final byte[] singleCharBuffer = new byte[1];
        for (final byte invalidChar : charsOutsideBase16Alphabet) {
            singleCharBuffer[0] = invalidChar;
            assertThrows(IllegalArgumentException.class,
                    () -> new Base16().decode(singleCharBuffer),
                    "Invalid Base16 char: " + (char) invalidChar);
        }
    }
}

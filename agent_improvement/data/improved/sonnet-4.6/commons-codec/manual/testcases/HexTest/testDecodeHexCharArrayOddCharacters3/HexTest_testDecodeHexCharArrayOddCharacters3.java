package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testDecodeHexCharArrayOddCharacters3 {

    /**
     * Verifies that decoding a char array with an odd number of hex characters
     * ('A', 'B', 'C' — three characters) throws a DecoderException, because
     * hex decoding requires pairs of characters to produce each output byte.
     */
    @Test
    void testDecodeHexCharArrayOddCharacters3() {
        char[] oddLengthHexChars = new char[] { 'A', 'B', 'C' };
        assertThrows(DecoderException.class, () -> Hex.decodeHex(oddLengthHexChars));
    }
}

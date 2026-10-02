package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testDecodeHexCharArrayOddCharacters5 {

    @Test
    void testDecodeHexCharArrayOddCharacters5() {
        // Hex decoding requires pairs of characters; an odd-length input must throw DecoderException.
        char[] oddLengthHex = { 'A', 'B', 'C', 'D', 'E' };
        assertThrows(DecoderException.class, () -> Hex.decodeHex(oddLengthHex));
    }
}

package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Hex#decodeHex(char[])} rejects a char array whose length
 * is odd. Hex decoding consumes characters in pairs (two hex digits per byte),
 * so an odd number of characters cannot be decoded and must raise a
 * {@link DecoderException}.
 */
public class HexTest_testDecodeHexCharArrayOddCharacters5 {

    @Test
    void testDecodeHexCharArrayOddCharacters5() {
        // Five characters is an odd count, so decoding must fail.
        final char[] oddLengthHex = { 'A', 'B', 'C', 'D', 'E' };

        assertThrows(DecoderException.class, () -> Hex.decodeHex(oddLengthHex));
    }
}

package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Hex#decodeHex(char[])} rejects a char array whose length
 * is odd. Hex decoding consumes two characters per byte, so an odd number of
 * characters cannot form a complete byte and must raise a {@link DecoderException}.
 */
public class HexTest_testDecodeHexCharArrayOddCharacters3 {

    @Test
    void decodeHexRejectsOddLengthCharArray() {
        // Three characters is an odd count and therefore cannot be decoded.
        final char[] oddLengthHex = { 'A', 'B', 'C' };

        assertThrows(DecoderException.class, () -> Hex.decodeHex(oddLengthHex));
    }
}

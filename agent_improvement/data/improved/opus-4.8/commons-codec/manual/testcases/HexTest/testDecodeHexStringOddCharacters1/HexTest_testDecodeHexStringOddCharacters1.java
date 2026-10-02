package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Hex#decodeHex(String)} rejects hex input that has an odd
 * number of characters. A valid hex string needs two characters per byte, so a
 * single-character input such as "A" cannot be decoded.
 */
public class HexTest_testDecodeHexStringOddCharacters1 {

    @Test
    void decodeHexWithOddNumberOfCharactersThrows() {
        final String oddLengthHex = "A";

        assertThrows(DecoderException.class, () -> Hex.decodeHex(oddLengthHex));
    }
}

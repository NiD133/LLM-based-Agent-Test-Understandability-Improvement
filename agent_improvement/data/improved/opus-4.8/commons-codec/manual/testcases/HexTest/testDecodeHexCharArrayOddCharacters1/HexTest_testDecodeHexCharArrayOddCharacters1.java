package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Hex#decodeHex(char[])} rejects an odd-length input.
 *
 * <p>Decoding hexadecimal requires two characters per byte, so any char array
 * with an odd number of elements cannot be decoded and must raise a
 * {@link DecoderException}.</p>
 */
public class HexTest_testDecodeHexCharArrayOddCharacters1 {

    @Test
    void testDecodeHexCharArrayOddCharacters1() {
        // A single character is an odd-length input and cannot form a full byte.
        final char[] oddLengthHex = { 'A' };

        assertThrows(DecoderException.class, () -> Hex.decodeHex(oddLengthHex));
    }
}

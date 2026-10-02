package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

/**
 * Verifies that decoding a hex string fails when the very first character
 * (position 0) is not a valid hexadecimal digit.
 */
public class HexTest_testDecodeBadCharacterPos0 {

    @Test
    void testDecodeBadCharacterPos0() {
        // "q0" has an illegal hex character ('q') at index 0, so decoding it
        // must raise a DecoderException rather than returning bytes.
        final String hexWithBadFirstChar = "q0";

        assertThrows(DecoderException.class, () -> new Hex().decode(hexWithBadFirstChar));
    }
}

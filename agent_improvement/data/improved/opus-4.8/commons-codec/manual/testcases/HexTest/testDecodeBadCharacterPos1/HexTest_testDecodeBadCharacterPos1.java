package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

/**
 * Verifies that decoding a hex string containing an illegal character
 * fails with a {@link DecoderException}.
 */
public class HexTest_testDecodeBadCharacterPos1 {

    @Test
    void testDecodeBadCharacterPos1() {
        // "0q" has a valid length but 'q' (at index 1) is not a hex digit,
        // so decoding must fail.
        final Hex hex = new Hex();
        assertThrows(DecoderException.class, () -> hex.decode("0q"));
    }
}

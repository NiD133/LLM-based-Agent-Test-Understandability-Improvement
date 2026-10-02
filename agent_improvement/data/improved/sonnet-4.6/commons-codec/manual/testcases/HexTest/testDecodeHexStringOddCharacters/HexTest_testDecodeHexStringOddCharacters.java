package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testDecodeHexStringOddCharacters {

    @Test
    void testDecodeHexStringOddCharacters() {
        // A hex string must have an even number of characters (two hex digits per byte).
        // Passing a single character "6" should trigger a DecoderException.
        assertThrows(DecoderException.class, () -> new Hex().decode("6"), "odd number of characters");
    }
}

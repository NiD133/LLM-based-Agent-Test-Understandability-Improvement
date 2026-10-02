package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testDecodeBadCharacterPos1 {

    @Test
    void testDecodeBadCharacterPos1() {
        // "0q" contains 'q' at index 1, which is not a valid hexadecimal digit
        assertThrows(DecoderException.class, () -> new Hex().decode("0q"));
    }
}

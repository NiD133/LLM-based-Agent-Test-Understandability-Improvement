package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testDecodeHexStringOddCharacters {

    private static final String ODD_LENGTH_HEX_STRING = "6";

    @Test
    void testDecodeHexStringOddCharacters() {
        assertThrows(DecoderException.class, () -> new Hex().decode(ODD_LENGTH_HEX_STRING), "odd number of characters");
    }
}

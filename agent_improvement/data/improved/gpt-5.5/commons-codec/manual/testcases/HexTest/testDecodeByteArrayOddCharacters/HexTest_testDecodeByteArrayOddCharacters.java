package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testDecodeByteArrayOddCharacters {

    private static final byte[] ODD_LENGTH_HEX_BYTES = { 65 };

    @Test
    void testDecodeByteArrayOddCharacters() {
        assertThrows(DecoderException.class, () -> new Hex().decode(ODD_LENGTH_HEX_BYTES), "odd number of characters");
    }
}

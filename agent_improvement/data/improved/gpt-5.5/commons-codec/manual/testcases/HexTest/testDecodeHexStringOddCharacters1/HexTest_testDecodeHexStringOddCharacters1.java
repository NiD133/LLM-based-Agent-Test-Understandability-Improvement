package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testDecodeHexStringOddCharacters1 {

    private void checkDecodeHexCharArrayOddCharacters(final String data) {
        assertThrows(DecoderException.class, () -> Hex.decodeHex(data));
    }

    @Test
    void testDecodeHexStringOddCharacters1() {
        checkDecodeHexCharArrayOddCharacters("A");
    }
}

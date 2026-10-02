package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testDecodeHexCharArrayOddCharacters5 {

    private void checkDecodeHexCharArrayOddCharacters(final char[] data) {
        assertThrows(DecoderException.class, () -> Hex.decodeHex(data));
    }

    @Test
    void testDecodeHexCharArrayOddCharacters5() {
        checkDecodeHexCharArrayOddCharacters(new char[] { 'A', 'B', 'C', 'D', 'E' });
    }
}

package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testDecodeHexCharArrayOddCharacters3 {

    private void checkDecodeHexCharArrayOddCharacters(final char[] data) {
        assertThrows(DecoderException.class, () -> Hex.decodeHex(data));
    }

    @Test
    void testDecodeHexCharArrayOddCharacters3() {
        checkDecodeHexCharArrayOddCharacters(new char[] { 'A', 'B', 'C' });
    }
}

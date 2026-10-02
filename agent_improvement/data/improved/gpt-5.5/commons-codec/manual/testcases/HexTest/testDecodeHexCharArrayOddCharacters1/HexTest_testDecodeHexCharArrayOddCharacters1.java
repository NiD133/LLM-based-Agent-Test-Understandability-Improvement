package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testDecodeHexCharArrayOddCharacters1 {

    @Test
    void testDecodeHexCharArrayOddCharacters1() {
        final char[] oddLengthHexInput = { 'A' };

        assertThrows(DecoderException.class, () -> Hex.decodeHex(oddLengthHexInput));
    }
}

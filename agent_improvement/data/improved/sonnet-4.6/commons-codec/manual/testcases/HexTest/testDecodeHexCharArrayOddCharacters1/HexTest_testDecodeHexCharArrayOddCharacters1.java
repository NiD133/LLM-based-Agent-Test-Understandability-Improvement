package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testDecodeHexCharArrayOddCharacters1 {

    @Test
    void testDecodeHexCharArrayOddCharacters1() {
        // A single hex character 'A' is an odd-length input; decodeHex requires pairs of characters.
        assertThrows(DecoderException.class, () -> Hex.decodeHex(new char[] { 'A' }));
    }
}

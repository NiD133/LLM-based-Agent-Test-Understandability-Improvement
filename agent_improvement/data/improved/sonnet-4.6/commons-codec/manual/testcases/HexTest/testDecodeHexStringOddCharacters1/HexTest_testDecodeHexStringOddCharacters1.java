package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testDecodeHexStringOddCharacters1 {

    @Test
    void testDecodeHexStringOddCharacters1() {
        // A single hex character "A" is an odd-length input; decodeHex must reject it.
        assertThrows(DecoderException.class, () -> Hex.decodeHex("A"));
    }
}

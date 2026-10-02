package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testDecodeBadCharacterPos1 {

    @Test
    void testDecodeBadCharacterPos1() {
        assertThrows(DecoderException.class, () -> new Hex().decode("0q"));
    }
}

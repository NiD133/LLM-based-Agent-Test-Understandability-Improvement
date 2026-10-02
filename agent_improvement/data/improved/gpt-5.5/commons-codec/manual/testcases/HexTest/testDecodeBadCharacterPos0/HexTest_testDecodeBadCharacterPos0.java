package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testDecodeBadCharacterPos0 {

    @Test
    void testDecodeBadCharacterPos0() {
        assertThrows(DecoderException.class, () -> new Hex().decode("q0"));
    }
}

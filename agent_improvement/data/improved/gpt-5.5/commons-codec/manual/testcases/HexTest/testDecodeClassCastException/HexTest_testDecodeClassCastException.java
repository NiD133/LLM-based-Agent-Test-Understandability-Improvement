package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testDecodeClassCastException {

    @Test
    void testDecodeClassCastException() {
        assertThrows(DecoderException.class, () -> new Hex().decode(new int[] { 65 }), "odd number of characters");
    }
}

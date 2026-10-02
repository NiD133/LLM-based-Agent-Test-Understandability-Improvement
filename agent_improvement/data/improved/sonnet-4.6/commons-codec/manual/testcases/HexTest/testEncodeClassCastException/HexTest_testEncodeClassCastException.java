package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testEncodeClassCastException {

    /**
     * Verifies that passing an unsupported type (int[]) to Hex.encode(Object) throws
     * EncoderException, because int[] cannot be cast to byte[] inside the encoder.
     */
    @Test
    void testEncodeClassCastException() {
        assertThrows(EncoderException.class, () -> new Hex().encode(new int[] { 65 }));
    }
}

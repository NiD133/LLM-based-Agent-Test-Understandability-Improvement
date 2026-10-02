package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testDecodeStringEmpty {

    @Test
    void testDecodeStringEmpty() throws DecoderException {
        byte[] decoded = (byte[]) new Hex().decode("");
        assertArrayEquals(new byte[0], decoded);
    }
}

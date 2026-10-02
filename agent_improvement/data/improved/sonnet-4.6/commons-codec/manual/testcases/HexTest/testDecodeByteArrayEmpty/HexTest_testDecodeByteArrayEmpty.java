package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testDecodeByteArrayEmpty {

    @Test
    void testDecodeByteArrayEmpty() throws DecoderException {
        byte[] emptyInput = new byte[0];
        byte[] result = new Hex().decode(emptyInput);
        assertArrayEquals(new byte[0], result);
    }
}

package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testDecodeHexStringEmpty {

    @Test
    void testDecodeHexStringEmpty() throws DecoderException {
        // Decoding an empty hex string should produce an empty byte array, not null or an error.
        byte[] result = Hex.decodeHex("");
        assertArrayEquals(new byte[0], result);
    }
}

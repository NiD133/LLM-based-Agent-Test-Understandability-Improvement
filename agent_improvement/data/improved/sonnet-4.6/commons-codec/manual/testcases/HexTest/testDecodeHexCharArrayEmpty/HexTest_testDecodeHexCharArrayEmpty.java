package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testDecodeHexCharArrayEmpty {

    @Test
    void testDecodeHexCharArrayEmpty() throws DecoderException {
        // Decoding an empty hex char array must produce an empty byte array
        assertArrayEquals(new byte[0], Hex.decodeHex(new char[0]));
    }
}

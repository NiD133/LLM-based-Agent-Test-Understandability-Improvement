package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

public class HexTest_testEncodeHexByteArrayEmpty {

    @Test
    void testEncodeHexByteArrayEmpty() {
        byte[] emptyInput = new byte[0];
        assertArrayEquals(new char[0], Hex.encodeHex(emptyInput));
        assertArrayEquals(new byte[0], new Hex().encode(emptyInput));
    }
}

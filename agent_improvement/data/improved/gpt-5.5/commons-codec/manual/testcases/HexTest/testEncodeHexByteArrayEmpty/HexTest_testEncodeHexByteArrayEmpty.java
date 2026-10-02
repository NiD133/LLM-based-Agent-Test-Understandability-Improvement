package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

public class HexTest_testEncodeHexByteArrayEmpty {

    @Test
    void testEncodeHexByteArrayEmpty() {
        assertArrayEquals(new char[0], Hex.encodeHex(new byte[0]));
        assertArrayEquals(new byte[0], new Hex().encode(new byte[0]));
    }
}

package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

public class HexTest_testEncodeByteArrayEmpty {

    @Test
    void testEncodeByteArrayEmpty() {
        assertArrayEquals(new byte[0], new Hex().encode(new byte[0]));
    }
}

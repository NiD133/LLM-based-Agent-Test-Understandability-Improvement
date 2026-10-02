package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

public class HexTest_testEncodeByteArrayEmpty {

    @Test
    void testEncodeByteArrayEmpty() {
        byte[] emptyInput = new byte[0];
        byte[] expectedOutput = new byte[0];

        byte[] actualOutput = new Hex().encode(emptyInput);

        assertArrayEquals(expectedOutput, actualOutput);
    }
}

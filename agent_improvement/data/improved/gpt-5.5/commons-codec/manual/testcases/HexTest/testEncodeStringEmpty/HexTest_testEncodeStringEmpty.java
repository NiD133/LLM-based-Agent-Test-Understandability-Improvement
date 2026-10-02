package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testEncodeStringEmpty {

    @Test
    void testEncodeStringEmpty() throws EncoderException {
        final char[] expectedHexCharacters = new char[0];

        assertArrayEquals(expectedHexCharacters, (char[]) new Hex().encode(""));
    }
}

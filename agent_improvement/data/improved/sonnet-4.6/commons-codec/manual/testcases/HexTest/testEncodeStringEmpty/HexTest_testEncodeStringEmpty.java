package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testEncodeStringEmpty {

    @Test
    void testEncodeStringEmpty() throws EncoderException {
        // Encoding an empty string should produce an empty hex char array
        char[] result = (char[]) new Hex().encode("");
        assertArrayEquals(new char[0], result);
    }
}

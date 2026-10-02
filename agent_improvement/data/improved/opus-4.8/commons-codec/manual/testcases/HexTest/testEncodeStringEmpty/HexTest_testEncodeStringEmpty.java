package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

/**
 * Verifies the behaviour of {@link Hex#encode(Object)} when given an empty String.
 */
public class HexTest_testEncodeStringEmpty {

    /**
     * Encoding an empty String must yield an empty {@code char[]}, since there are no
     * bytes to expand into hexadecimal character pairs.
     */
    @Test
    void testEncodeStringEmpty() throws EncoderException {
        final Object encoded = new Hex().encode("");

        final char[] expectedHexChars = new char[0];
        assertArrayEquals(expectedHexChars, (char[]) encoded);
    }
}

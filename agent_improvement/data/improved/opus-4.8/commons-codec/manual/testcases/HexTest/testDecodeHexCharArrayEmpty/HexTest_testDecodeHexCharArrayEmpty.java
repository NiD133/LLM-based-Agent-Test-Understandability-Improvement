package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link Hex#decodeHex(char[])} with an empty input.
 */
public class HexTest_testDecodeHexCharArrayEmpty {

    /**
     * Decoding an empty char array must yield an empty byte array,
     * since every two hex characters map to a single byte.
     */
    @Test
    void testDecodeHexCharArrayEmpty() throws DecoderException {
        final char[] emptyHexChars = new char[0];

        final byte[] decoded = Hex.decodeHex(emptyHexChars);

        assertArrayEquals(new byte[0], decoded);
    }
}

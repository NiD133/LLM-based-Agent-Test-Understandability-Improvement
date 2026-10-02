package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

/**
 * Verifies {@link Hex#decodeHex(String)} handling of an empty input String.
 */
public class HexTest_testDecodeHexStringEmpty {

    /**
     * Decoding an empty hexadecimal String should yield an empty byte array,
     * since it takes two characters to represent one byte.
     */
    @Test
    void testDecodeHexStringEmpty() throws DecoderException {
        final byte[] decoded = Hex.decodeHex("");

        assertArrayEquals(new byte[0], decoded);
    }
}

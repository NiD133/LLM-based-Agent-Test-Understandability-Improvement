package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link Hex#decodeHex(String)} for the empty-input edge case.
 */
public class HexTest_testDecodeHexStringEmpty {

    /**
     * Decoding an empty hex String should yield an empty byte array,
     * since two hex characters map to a single byte.
     */
    @Test
    void testDecodeHexStringEmpty() throws DecoderException {
        final byte[] decoded = Hex.decodeHex("");

        assertArrayEquals(new byte[0], decoded);
    }
}

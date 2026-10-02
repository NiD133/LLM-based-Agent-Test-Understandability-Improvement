package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

/**
 * Verifies {@link Hex#decodeHex(String)} on the empty-string boundary case.
 */
public class HexTest_testDecodeHexStringEmpty {

    @Test
    void testDecodeHexStringEmpty() throws DecoderException {
        // Decoding an empty hex string yields an empty byte array (no bytes to decode).
        final byte[] decoded = Hex.decodeHex("");

        assertArrayEquals(new byte[0], decoded);
    }
}

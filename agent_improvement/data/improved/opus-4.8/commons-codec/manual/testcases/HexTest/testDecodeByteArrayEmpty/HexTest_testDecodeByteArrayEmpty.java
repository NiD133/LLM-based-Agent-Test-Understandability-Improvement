package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Hex#decode(byte[])} maps an empty input byte array to an
 * empty output byte array.
 */
public class HexTest_testDecodeByteArrayEmpty {

    /**
     * Decoding an empty byte array of hex characters should yield an empty
     * byte array (no bytes in, no bytes out).
     */
    @Test
    void testDecodeByteArrayEmpty() throws DecoderException {
        final byte[] emptyInput = new byte[0];

        final byte[] decoded = new Hex().decode(emptyInput);

        assertArrayEquals(new byte[0], decoded);
    }
}

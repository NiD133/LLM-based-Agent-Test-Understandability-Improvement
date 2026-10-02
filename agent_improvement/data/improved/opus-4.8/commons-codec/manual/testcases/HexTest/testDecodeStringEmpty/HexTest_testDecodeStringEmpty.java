package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Hex#decode(Object)} handles an empty hexadecimal String.
 */
public class HexTest_testDecodeStringEmpty {

    /**
     * Decoding an empty String should yield an empty byte array, since every two
     * hexadecimal characters decode to a single byte and there are no characters to decode.
     */
    @Test
    void testDecodeStringEmpty() throws DecoderException {
        final byte[] decoded = (byte[]) new Hex().decode("");

        assertArrayEquals(new byte[0], decoded);
    }
}

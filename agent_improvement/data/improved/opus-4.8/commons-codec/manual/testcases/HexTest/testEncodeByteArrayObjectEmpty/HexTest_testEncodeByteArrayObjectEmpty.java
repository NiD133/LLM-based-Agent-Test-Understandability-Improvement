package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link Hex#encode(Object)} with an empty byte array argument.
 */
public class HexTest_testEncodeByteArrayObjectEmpty {

    /**
     * Encoding an empty {@code byte[]} (passed as an {@code Object}) should
     * yield an empty {@code char[]}, since each byte maps to two hex characters
     * and there are no bytes to convert.
     */
    @Test
    void testEncodeByteArrayObjectEmpty() throws EncoderException {
        final byte[] emptyInput = new byte[0];

        final char[] encoded = (char[]) new Hex().encode((Object) emptyInput);

        assertArrayEquals(new char[0], encoded);
    }
}

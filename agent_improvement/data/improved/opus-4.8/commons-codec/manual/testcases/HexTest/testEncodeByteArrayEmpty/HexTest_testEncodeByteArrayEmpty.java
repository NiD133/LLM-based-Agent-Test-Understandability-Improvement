package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies the edge case of {@link Hex#encode(byte[])} when given an empty input.
 */
public class HexTest_testEncodeByteArrayEmpty {

    /**
     * Encoding an empty byte array should yield an empty byte array,
     * since there are no bytes to convert into hexadecimal characters.
     */
    @Test
    void testEncodeByteArrayEmpty() {
        final byte[] emptyInput = new byte[0];

        final byte[] encoded = new Hex().encode(emptyInput);

        assertArrayEquals(new byte[0], encoded);
    }
}

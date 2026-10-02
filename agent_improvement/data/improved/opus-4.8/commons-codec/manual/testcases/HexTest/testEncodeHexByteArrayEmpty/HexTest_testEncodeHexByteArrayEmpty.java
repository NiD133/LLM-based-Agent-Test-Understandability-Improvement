package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that encoding an empty byte array produces an empty result,
 * for both the static {@link Hex#encodeHex(byte[])} method and the
 * instance {@link Hex#encode(byte[])} method.
 */
public class HexTest_testEncodeHexByteArrayEmpty {

    @Test
    void testEncodeHexByteArrayEmpty() {
        final byte[] emptyInput = new byte[0];

        // Static encoder: empty input -> empty char[] of hex digits.
        assertArrayEquals(new char[0], Hex.encodeHex(emptyInput));

        // Instance encoder: empty input -> empty byte[] of hex-digit bytes.
        assertArrayEquals(new byte[0], new Hex().encode(emptyInput));
    }
}

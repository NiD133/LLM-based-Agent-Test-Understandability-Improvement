package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link Hex#encodeHexString(byte[])} for an all-zero byte array.
 */
public class HexTest_testEncodeHexByteString_ByteArrayOfZeroes {

    /**
     * Each byte is encoded as two hexadecimal characters, so an array of 36
     * zero-valued bytes must produce a string of 72 '0' characters.
     */
    @Test
    void testEncodeHexByteString_ByteArrayOfZeroes() {
        final byte[] allZeroBytes = new byte[36];

        final String hex = Hex.encodeHexString(allZeroBytes);

        // 36 bytes * 2 hex chars per byte = 72 '0' characters.
        final String expectedHex = "000000000000000000000000000000000000000000000000000000000000000000000000";
        assertEquals(expectedHex, hex);
    }
}

package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Hex#encodeHex(byte[])} encodes an all-zero byte array
 * into the expected sequence of '0' hexadecimal characters.
 */
public class HexTest_testEncodeHexByteArrayZeroes {

    @Test
    void testEncodeHexByteArrayZeroes() {
        // A byte[] of all zeroes: each byte (0x00) encodes to the two characters "00".
        final int byteCount = 36;
        final char[] encoded = Hex.encodeHex(new byte[byteCount]);

        // Encoding doubles the length: 36 zero bytes -> 72 '0' characters.
        final char[] expectedChars = new char[byteCount * 2];
        Arrays.fill(expectedChars, '0');
        final String expected = new String(expectedChars);
        assertEquals(expected, new String(encoded));
    }
}

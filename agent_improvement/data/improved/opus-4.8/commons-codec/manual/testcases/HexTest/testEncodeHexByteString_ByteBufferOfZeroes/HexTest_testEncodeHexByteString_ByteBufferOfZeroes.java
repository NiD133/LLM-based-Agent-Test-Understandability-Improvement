package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Hex#encodeHexString(ByteBuffer)} renders an all-zero
 * byte buffer as the corresponding string of hexadecimal "00" pairs.
 */
public class HexTest_testEncodeHexByteString_ByteBufferOfZeroes {

    /** Number of zero-valued bytes to encode. */
    private static final int BYTE_COUNT = 36;

    /**
     * Each byte becomes two hex characters, so a freshly allocated (and
     * therefore zero-filled) buffer of {@value #BYTE_COUNT} bytes must encode
     * to {@value #BYTE_COUNT} occurrences of "00".
     */
    @Test
    void testEncodeHexByteString_ByteBufferOfZeroes() {
        final ByteBuffer zeroBytes = ByteBuffer.allocate(BYTE_COUNT);

        final String encoded = Hex.encodeHexString(zeroBytes);

        // BYTE_COUNT (36) bytes => 36 "00" pairs => 72 '0' characters.
        final String expected =
                "000000000000000000000000000000000000000000000000000000000000000000000000";
        assertEquals(expected, encoded);
    }
}

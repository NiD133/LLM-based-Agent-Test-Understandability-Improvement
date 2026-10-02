package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link Hex#encodeHexString(byte[], boolean)} when requesting
 * lower-case output.
 */
public class HexTest_testEncodeHexByteString_ByteArrayBoolean_ToLowerCase {

    /**
     * The byte value {@code 10} encodes to the hexadecimal digits {@code 0a};
     * with {@code toLowerCase = true} the letter digit must be lower-case.
     */
    @Test
    void testEncodeHexByteString_ByteArrayBoolean_ToLowerCase() {
        final byte[] input = { 10 };
        final boolean toLowerCase = true;

        final String hex = Hex.encodeHexString(input, toLowerCase);

        assertEquals("0a", hex);
    }
}

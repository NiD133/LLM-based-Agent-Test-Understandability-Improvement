package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Hex#encodeHexString(byte[], boolean)} produces upper-case
 * hexadecimal when {@code toLowerCase} is {@code false}.
 */
public class HexTest_testEncodeHexByteString_ByteArrayBoolean_ToUpperCase {

    @Test
    void testEncodeHexByteString_ByteArrayBoolean_ToUpperCase() {
        final byte[] input = { 10 };
        final boolean toLowerCase = false;

        final String encoded = Hex.encodeHexString(input, toLowerCase);

        // The byte 10 encodes to the two upper-case hex digits "0A".
        assertEquals("0A", encoded);
    }
}

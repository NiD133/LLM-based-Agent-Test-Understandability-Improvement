package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class Base58Test_testLeadingZeros {

    private static final byte[] BYTES_WITH_TWO_LEADING_ZEROS = { 0, 0, 1, 2, 3 };

    private static final String TWO_LEADING_ZEROES_AS_BASE58_PREFIX = "11";

    @Test
    void testLeadingZeros() {
        final byte[] encoded = new Base58().encode(BYTES_WITH_TWO_LEADING_ZEROS);
        final String encodedString = new String(encoded);

        assertTrue(encodedString.startsWith(TWO_LEADING_ZEROES_AS_BASE58_PREFIX),
                "Leading zeros should encode as '1' characters");

        final byte[] decoded = new Base58().decode(encoded);
        assertArrayEquals(BYTES_WITH_TWO_LEADING_ZEROS, decoded,
                "Decoded should match original including leading zeros");
    }
}

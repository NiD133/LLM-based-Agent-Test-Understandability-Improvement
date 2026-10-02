package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class Base58Test_testLeadingZeros {

    @Test
    void testLeadingZeros() {
        // Leading zero bytes must round-trip through Base58 encode/decode.
        // In Base58, each leading 0x00 byte is represented as the character '1'.
        final byte[] input = { 0, 0, 1, 2, 3 };

        final byte[] encoded = new Base58().encode(input);
        final String encodedStr = new String(encoded);

        // Two leading zero bytes → two leading '1' characters in the encoded string.
        assertTrue(encodedStr.startsWith("11"), "Leading zeros should encode as '1' characters");

        // Decoding must restore the original leading zero bytes exactly.
        final byte[] decoded = new Base58().decode(encoded);
        assertArrayEquals(input, decoded, "Decoded should match original including leading zeros");
    }
}

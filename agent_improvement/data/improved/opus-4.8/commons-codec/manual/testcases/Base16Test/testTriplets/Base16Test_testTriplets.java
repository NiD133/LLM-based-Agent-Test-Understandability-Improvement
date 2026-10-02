package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Base16} encodes three-byte inputs into their six-character
 * upper-case hexadecimal representation.
 */
public class Base16Test_testTriplets {

    /**
     * Encodes the triplets {@code {0, 0, n}} for every nibble value {@code n} in
     * {@code 0..15}. Each triplet must encode to {@code "0000" + <two hex chars for n>},
     * for example {@code {0, 0, 10}} becomes {@code "00000A"}.
     */
    @Test
    void testTriplets() {
        for (int n = 0; n <= 0x0F; n++) {
            final byte[] triplet = { (byte) 0, (byte) 0, (byte) n };
            final String expected = String.format("%06X", n);
            final String actual = new String(new Base16().encode(triplet));
            assertEquals(expected, actual, "encoding triplet {0, 0, " + n + "}");
        }
    }
}

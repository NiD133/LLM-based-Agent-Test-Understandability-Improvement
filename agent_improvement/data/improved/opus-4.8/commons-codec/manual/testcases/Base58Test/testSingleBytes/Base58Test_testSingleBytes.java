package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Base58} round-trips every possible single non-zero byte:
 * encoding a one-byte array and then decoding the result must yield the original byte.
 */
public class Base58Test_testSingleBytes {

    @Test
    void testSingleBytes() {
        // Every single-byte value from 1 to 255 must survive an encode/decode round-trip.
        for (int byteValue = 1; byteValue <= 255; byteValue++) {
            final byte[] original = { (byte) byteValue };

            final byte[] encoded = new Base58().encode(original);
            final byte[] decoded = new Base58().decode(encoded);

            assertArrayEquals(original, decoded, "Failed for byte value: " + byteValue);
        }
    }
}

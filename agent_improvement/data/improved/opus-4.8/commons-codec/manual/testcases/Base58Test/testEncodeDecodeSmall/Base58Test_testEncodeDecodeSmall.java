package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

/**
 * Verifies that Base58 encoding followed by decoding round-trips small byte
 * arrays back to their original content.
 */
public class Base58Test_testEncodeDecodeSmall {

    /** Exclusive upper bound for the small input lengths exercised by the test. */
    private static final int MAX_LENGTH = 12;

    /**
     * For every length from 0 up to {@link #MAX_LENGTH}, encodes a byte array and
     * confirms that decoding the result reproduces the original bytes.
     */
    @Test
    void testEncodeDecodeSmall() {
        for (int i = 0; i < MAX_LENGTH; i++) {
            final int length = i;
            // Build an array of the current length where every byte equals the length.
            final byte[] original = new byte[length];
            Arrays.fill(original, (byte) length);

            final byte[] encoded = new Base58().encode(original);
            final byte[] decoded = new Base58().decode(encoded);

            assertArrayEquals(original, decoded,
                    () -> String.format("Failed for length %,d: %s", length, Arrays.toString(original)));
        }
    }
}

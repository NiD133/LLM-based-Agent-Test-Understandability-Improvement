package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;
import java.util.Random;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Base58} encoding followed by decoding restores the
 * original bytes for a range of short, randomly generated inputs.
 */
public class Base58Test_testEncodeDecodeSmallRandom {

    /** Exclusive upper bound for the input length swept by the round-trip test. */
    private static final int MAX_INPUT_LENGTH = 12;

    private final Random random = new Random();

    /**
     * Encodes then decodes random byte arrays of every length from 0 up to
     * {@link #MAX_INPUT_LENGTH} (exclusive) and asserts the round trip is loss-less.
     */
    @Test
    void testEncodeDecodeSmallRandom() {
        for (int length = 0; length < MAX_INPUT_LENGTH; length++) {
            final byte[] original = new byte[length];
            random.nextBytes(original);

            final byte[] encoded = new Base58().encode(original);
            final byte[] decoded = new Base58().decode(encoded);

            assertArrayEquals(original, decoded,
                    () -> String.format("Failed for length %,d: %s", original.length, Arrays.toString(original)));
        }
    }
}

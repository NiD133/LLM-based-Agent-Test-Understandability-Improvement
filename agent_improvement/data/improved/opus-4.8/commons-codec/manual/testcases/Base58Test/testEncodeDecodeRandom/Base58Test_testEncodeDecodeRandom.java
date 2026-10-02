package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;
import java.util.Random;

import org.junit.jupiter.api.Test;

/**
 * Verifies that Base58 encoding followed by decoding is a round-trip: decoding the
 * encoded form of random binary data reproduces the original bytes exactly.
 */
public class Base58Test_testEncodeDecodeRandom {

    /** Exclusive upper bound for the randomly chosen length of each test input. */
    private static final int MAX_LENGTH = 10_000;

    /** Number of random round-trip iterations to perform. */
    private static final int ITERATIONS = 4;

    private final Random random = new Random();

    @Test
    void testEncodeDecodeRandom() {
        for (int iteration = 1; iteration <= ITERATIONS; iteration++) {
            // Generate a random, non-empty byte array (length 1..MAX_LENGTH).
            final byte[] original = new byte[random.nextInt(MAX_LENGTH) + 1];
            random.nextBytes(original);

            final byte[] encoded = new Base58().encode(original);
            final byte[] decoded = new Base58().decode(encoded);

            final int currentIteration = iteration;
            assertArrayEquals(original, decoded,
                    () -> String.format("Round-trip failed on iteration %,d: %s",
                            currentIteration, Arrays.toString(original)));
        }
    }
}

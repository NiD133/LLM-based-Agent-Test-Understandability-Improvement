package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;
import java.util.Random;

import org.junit.jupiter.api.Test;

public class Base58Test_testEncodeDecodeRandom {

    /** Maximum byte-array length for randomly generated test data. */
    private static final int MAX_DATA_LENGTH = 10_000;

    /** Number of independent round-trip encode/decode iterations to run. */
    private static final int NUM_ITERATIONS = 4;

    private final Random random = new Random();

    @Test
    void testEncodeDecodeRandom() {
        for (int iteration = 1; iteration <= NUM_ITERATIONS; iteration++) {
            final byte[] original = new byte[random.nextInt(MAX_DATA_LENGTH) + 1];
            random.nextBytes(original);

            final byte[] encoded = new Base58().encode(original);
            final byte[] decoded = new Base58().decode(encoded);

            final int currentIteration = iteration;
            assertArrayEquals(original, decoded,
                    () -> String.format("Round-trip encode/decode failed at iteration %,d: %s",
                            currentIteration, Arrays.toString(original)));
        }
    }
}

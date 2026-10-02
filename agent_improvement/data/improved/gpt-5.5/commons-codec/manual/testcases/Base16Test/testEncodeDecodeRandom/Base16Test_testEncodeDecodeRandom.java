package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Random;

import org.junit.jupiter.api.Test;

public class Base16Test_testEncodeDecodeRandom {

    private static final int RANDOM_TRIAL_COUNT = 4;
    private static final int MAX_RANDOM_BYTE_COUNT = 10000;

    private final Random random = new Random();

    Random getRandom() {
        return this.random;
    }

    @Test
    void testEncodeDecodeRandom() {
        for (int trial = 1; trial <= RANDOM_TRIAL_COUNT; trial++) {
            final int byteCount = getRandom().nextInt(MAX_RANDOM_BYTE_COUNT) + 1;
            final byte[] originalData = new byte[byteCount];
            getRandom().nextBytes(originalData);

            final byte[] encodedData = new Base16().encode(originalData);
            final byte[] decodedData = new Base16().decode(encodedData);

            assertArrayEquals(originalData, decodedData);
        }
    }
}

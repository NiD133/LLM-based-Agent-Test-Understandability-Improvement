package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

public class Base58Test_testEncodeDecodeRandom {

    private static final int RANDOM_DATA_LENGTH_BOUND = 10_000;

    private static final int FIRST_RANDOM_CASE_INDEX = 1;

    private static final int RANDOM_CASE_COUNT_EXCLUSIVE = 5;

    private static void assertDecodedBytesMatchOriginal(final byte[] expectedData, final byte[] decodedData, final int caseIndex) {
        final AtomicInteger counter = new AtomicInteger(caseIndex);
        assertArrayEquals(expectedData, decodedData, () -> String.format("Failed for length %,d: %s", counter.get(), Arrays.toString(expectedData)));
    }

    private final Random random = new Random();

    @Test
    void testEncodeDecodeRandom() {
        for (int caseIndex = FIRST_RANDOM_CASE_INDEX; caseIndex < RANDOM_CASE_COUNT_EXCLUSIVE; caseIndex++) {
            final byte[] data = new byte[random.nextInt(RANDOM_DATA_LENGTH_BOUND) + 1];
            random.nextBytes(data);

            final byte[] encodedData = new Base58().encode(data);
            final byte[] decodedData = new Base58().decode(encodedData);

            assertDecodedBytesMatchOriginal(data, decodedData, caseIndex);
        }
    }
}

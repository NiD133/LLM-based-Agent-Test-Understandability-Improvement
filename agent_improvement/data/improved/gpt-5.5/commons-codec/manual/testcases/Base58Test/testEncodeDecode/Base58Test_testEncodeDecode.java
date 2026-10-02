package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

public class Base58Test_testEncodeDecode {

    private static final int MAX_RANDOM_DATA_LENGTH_EXCLUSIVE = 10_000;

    private final Random random = new Random();

    @Test
    void testEncodeDecode() {
        for (int repeatedByteValue = 1; repeatedByteValue < 5; repeatedByteValue++) {
            final byte[] originalData = createRandomLengthArrayFilledWith(repeatedByteValue);
            final byte[] encodedData = new Base58().encode(originalData);
            final byte[] decodedData = new Base58().decode(encodedData);

            assertRoundTripPreservesData(originalData, decodedData, repeatedByteValue);
        }
    }

    private byte[] createRandomLengthArrayFilledWith(final int repeatedByteValue) {
        final byte[] data = new byte[random.nextInt(MAX_RANDOM_DATA_LENGTH_EXCLUSIVE) + 1];
        Arrays.fill(data, (byte) repeatedByteValue);
        return data;
    }

    private static void assertRoundTripPreservesData(final byte[] originalData, final byte[] decodedData, final int dataLengthLabel) {
        final AtomicInteger counter = new AtomicInteger(dataLengthLabel);
        assertArrayEquals(originalData, decodedData, () -> String.format("Failed for length %,d: %s", counter.get(), Arrays.toString(originalData)));
    }
}

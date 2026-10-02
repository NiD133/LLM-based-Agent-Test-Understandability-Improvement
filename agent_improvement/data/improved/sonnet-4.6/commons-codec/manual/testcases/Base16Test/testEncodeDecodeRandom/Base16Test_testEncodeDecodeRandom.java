package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Random;

import org.junit.jupiter.api.Test;

public class Base16Test_testEncodeDecodeRandom {

    private static final int ROUND_TRIP_ITERATIONS = 4;
    private static final int MAX_DATA_LENGTH = 10000;

    private final Random random = new Random();

    @Test
    void testEncodeDecodeRandom() {
        for (int i = 0; i < ROUND_TRIP_ITERATIONS; i++) {
            final int length = random.nextInt(MAX_DATA_LENGTH) + 1;
            final byte[] originalData = new byte[length];
            random.nextBytes(originalData);

            final byte[] encodedBytes = new Base16().encode(originalData);
            final byte[] decodedBytes = new Base16().decode(encodedBytes);

            assertArrayEquals(originalData, decodedBytes,
                "Base16 encode then decode should restore the original byte array");
        }
    }
}

package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;
import java.util.Random;

import org.junit.jupiter.api.Test;

public class Base58Test_testEncodeDecodeSmallRandom {

    private static final int MAX_RANDOM_INPUT_LENGTH_EXCLUSIVE = 12;

    private final Random random = new Random();

    @Test
    void testEncodeDecodeSmallRandom() {
        final Base58 base58 = new Base58();

        for (int inputLength = 0; inputLength < MAX_RANDOM_INPUT_LENGTH_EXCLUSIVE; inputLength++) {
            final byte[] originalBytes = randomBytes(inputLength);
            final byte[] encodedBytes = base58.encode(originalBytes);
            final byte[] decodedBytes = base58.decode(encodedBytes);

            assertRoundTripPreservesBytes(originalBytes, decodedBytes, inputLength);
        }
    }

    private byte[] randomBytes(final int length) {
        final byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        return bytes;
    }

    private static void assertRoundTripPreservesBytes(
            final byte[] expectedBytes,
            final byte[] actualBytes,
            final int inputLength) {
        assertArrayEquals(
                expectedBytes,
                actualBytes,
                () -> String.format("Failed for length %,d: %s", inputLength, Arrays.toString(expectedBytes)));
    }
}

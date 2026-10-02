package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;
import java.util.Random;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Base58} performs a lossless encode-then-decode round trip:
 * decoding a freshly encoded byte array must reproduce the original bytes.
 */
public class Base58Test_testEncodeDecode {

    /** Exclusive upper bound for the randomly chosen length of each test payload. */
    private static final int MAX_LENGTH = 10_000;

    private final Random random = new Random();

    /**
     * Encodes and then decodes byte arrays of random length, asserting that the
     * decoded result matches the original input. Each iteration uses a different
     * fill byte so that several distinct payloads are exercised.
     */
    @Test
    void testEncodeDecode() {
        for (int fillByte = 1; fillByte < 5; fillByte++) {
            final byte[] original = new byte[random.nextInt(MAX_LENGTH) + 1];
            Arrays.fill(original, (byte) fillByte);

            final byte[] encoded = new Base58().encode(original);
            final byte[] decoded = new Base58().decode(encoded);

            final int iteration = fillByte;
            assertArrayEquals(original, decoded,
                    () -> String.format("Round trip failed for length %,d: %s",
                            iteration, Arrays.toString(original)));
        }
    }
}

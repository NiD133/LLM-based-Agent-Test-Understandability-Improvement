package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Test;

public class Base58Test_testEncodeDecodeSmallRandom {

    private final Random random = new Random();

    @Test
    void testEncodeDecodeSmallRandom() {
        for (int length = 0; length < 12; length++) {
            final byte[] original = new byte[length];
            random.nextBytes(original);

            final byte[] encoded = new Base58().encode(original);
            final byte[] decoded = new Base58().decode(encoded);

            final int capturedLength = length;
            assertArrayEquals(original, decoded,
                () -> String.format("Encode-decode round-trip failed for length %,d: %s",
                    capturedLength, Arrays.toString(original)));
        }
    }
}

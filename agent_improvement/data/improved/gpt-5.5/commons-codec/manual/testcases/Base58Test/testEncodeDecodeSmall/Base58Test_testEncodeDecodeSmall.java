package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class Base58Test_testEncodeDecodeSmall {

    private static final int TESTED_LENGTHS = 12;

    private static void assertArrayEqualsAt(final byte[] expected, final byte[] actual, final int length) {
        assertArrayEquals(expected, actual, () -> String.format("Failed for length %,d: %s", length, Arrays.toString(expected)));
    }

    @Test
    void testEncodeDecodeSmall() {
        for (int length = 0; length < TESTED_LENGTHS; length++) {
            final byte[] data = new byte[length];
            Arrays.fill(data, (byte) length);

            final byte[] encoded = new Base58().encode(data);
            final byte[] decoded = new Base58().decode(encoded);

            assertArrayEqualsAt(data, decoded, length);
        }
    }
}

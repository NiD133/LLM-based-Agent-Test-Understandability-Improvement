package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class Base58Test_testEncodeDecodeSmall {

    @Test
    void testEncodeDecodeSmall() {
        for (int length = 0; length < 12; length++) {
            final byte[] data = new byte[length];
            Arrays.fill(data, (byte) length);
            final byte[] encoded = new Base58().encode(data);
            final byte[] decoded = new Base58().decode(encoded);
            final int capturedLength = length;
            assertArrayEquals(data, decoded,
                    () -> String.format("Failed for length %,d: %s", capturedLength, Arrays.toString(data)));
        }
    }
}

package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Random;

import org.junit.jupiter.api.Test;

public class Base16Test_testEncodeDecodeSmall {

    private static final int TEST_DATA_LENGTH_LIMIT = 12;

    private final Random random = new Random();

    /**
     * @return the random.
     */
    Random getRandom() {
        return this.random;
    }

    private String toString(final byte[] data) {
        final StringBuilder buf = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            buf.append(data[i]);
            if (i != data.length - 1) {
                buf.append(",");
            }
        }
        return buf.toString();
    }

    @Test
    void testEncodeDecodeSmall() {
        for (int length = 0; length < TEST_DATA_LENGTH_LIMIT; length++) {
            final byte[] originalData = new byte[length];
            getRandom().nextBytes(originalData);

            final byte[] encodedData = new Base16().encode(originalData);
            final byte[] decodedData = new Base16().decode(encodedData);

            assertArrayEquals(originalData, decodedData, toString(originalData) + " equals " + toString(decodedData));
        }
    }
}

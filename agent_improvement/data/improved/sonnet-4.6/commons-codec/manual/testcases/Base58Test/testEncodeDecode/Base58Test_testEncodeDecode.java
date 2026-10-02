package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;
import java.util.Random;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class Base58Test_testEncodeDecode {

    // Upper bound on the random byte-array length used in each round-trip test.
    private static final int MAX_DATA_LENGTH = 10_000;

    private final Random random = new Random();

    /**
     * Verifies that Base58 encoding followed by decoding produces the original bytes,
     * for input arrays filled with each of the byte values 1–4.
     */
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4})
    void testEncodeDecode(final int fillValue) {
        final byte[] data = new byte[random.nextInt(MAX_DATA_LENGTH) + 1];
        Arrays.fill(data, (byte) fillValue);

        final byte[] encoded = new Base58().encode(data);
        final byte[] decoded = new Base58().decode(encoded);

        assertArrayEquals(data, decoded,
            () -> String.format("Encode-decode round-trip failed for fill byte %,d: %s",
                fillValue, Arrays.toString(data)));
    }
}

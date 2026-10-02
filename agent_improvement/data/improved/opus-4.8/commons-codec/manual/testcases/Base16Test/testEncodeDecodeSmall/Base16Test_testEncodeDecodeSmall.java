package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Random;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Base16} encoding followed by decoding is a lossless
 * round-trip for small inputs.
 */
public class Base16Test_testEncodeDecodeSmall {

    /** Largest input length exercised by the round-trip test (exclusive). */
    private static final int MAX_INPUT_LENGTH = 12;

    private final Random random = new Random();

    /**
     * Renders a byte array as a comma-separated list, used to build a readable
     * assertion message describing the data being compared.
     */
    private String formatBytes(final byte[] data) {
        final StringBuilder buf = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            buf.append(data[i]);
            if (i != data.length - 1) {
                buf.append(",");
            }
        }
        return buf.toString();
    }

    /**
     * Encodes then decodes random byte arrays of length 0 through 11 and asserts
     * that decoding recovers the original bytes.
     */
    @Test
    void testEncodeDecodeSmall() {
        for (int length = 0; length < MAX_INPUT_LENGTH; length++) {
            final byte[] original = new byte[length];
            random.nextBytes(original);

            final byte[] encoded = new Base16().encode(original);
            final byte[] roundTripped = new Base16().decode(encoded);

            assertArrayEquals(original, roundTripped,
                    formatBytes(original) + " equals " + formatBytes(roundTripped));
        }
    }
}

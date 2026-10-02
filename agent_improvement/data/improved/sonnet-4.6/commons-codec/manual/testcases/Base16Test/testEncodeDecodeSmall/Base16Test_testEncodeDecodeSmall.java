package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Random;

import org.junit.jupiter.api.Test;

public class Base16Test_testEncodeDecodeSmall {

    private final Random random = new Random();

    /** Formats a byte array as comma-separated decimal values for use in assertion failure messages. */
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

    // Verifies that Base16 encode followed by decode is a lossless round-trip for arrays of sizes 0 to 11.
    @Test
    void testEncodeDecodeSmall() {
        for (int i = 0; i < 12; i++) {
            final byte[] original = new byte[i];
            random.nextBytes(original);
            final byte[] encoded = new Base16().encode(original);
            final byte[] decoded = new Base16().decode(encoded);
            assertArrayEquals(original, decoded,
                    formatBytes(original) + " equals " + formatBytes(decoded));
        }
    }
}

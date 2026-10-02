package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Base16Test_testPairs {

    private static final String[] EXPECTED_ZERO_PREFIX_ENCODINGS = {
        "0000", "0001", "0002", "0003", "0004", "0005",
        "0006", "0007", "0008", "0009", "000A", "000B",
        "000C", "000D", "000E", "000F", "0010", "0011"
    };

    @Test
    void testPairs() {
        for (int value = 0; value < EXPECTED_ZERO_PREFIX_ENCODINGS.length; value++) {
            assertEncodesZeroPrefixedPair(value, EXPECTED_ZERO_PREFIX_ENCODINGS[value]);
        }

        for (int i = -128; i <= 127; i++) {
            final byte[] test = { (byte) i, (byte) i };
            assertArrayEquals(test, new Base16().decode(new Base16().encode(test)));
        }
    }

    private void assertEncodesZeroPrefixedPair(final int value, final String expected) {
        assertEquals(expected, new String(new Base16().encode(new byte[] { (byte) 0, (byte) value })));
    }
}

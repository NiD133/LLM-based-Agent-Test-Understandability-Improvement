package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

public class Base58Test_testSingleBytes {

    private static final int FIRST_NON_ZERO_BYTE_VALUE = 1;
    private static final int LAST_UNSIGNED_BYTE_VALUE = 255;

    @Test
    void testSingleBytes() {
        for (int byteValue = FIRST_NON_ZERO_BYTE_VALUE; byteValue <= LAST_UNSIGNED_BYTE_VALUE; byteValue++) {
            assertRoundTripForSingleByte(byteValue);
        }
    }

    private void assertRoundTripForSingleByte(final int byteValue) {
        final byte[] data = { (byte) byteValue };

        final byte[] enc = new Base58().encode(data);
        final byte[] dec = new Base58().decode(enc);

        assertArrayEquals(data, dec, "Failed for byte value: " + byteValue);
    }
}

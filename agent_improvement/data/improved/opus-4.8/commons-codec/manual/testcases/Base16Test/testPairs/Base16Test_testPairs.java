package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Base16Test_testPairs {

    /**
     * Encodes a two-byte input with a fresh {@link Base16} codec and asserts the
     * resulting upper-case hex string.
     *
     * @param firstByte    the first byte of the input.
     * @param secondByte   the second byte of the input.
     * @param expectedHex  the expected Base16 (hex) encoding of the two bytes.
     */
    private void assertEncodesTo(final int firstByte, final int secondByte, final String expectedHex) {
        final byte[] input = { (byte) firstByte, (byte) secondByte };
        final String actualHex = new String(new Base16().encode(input));
        assertEquals(expectedHex, actualHex);
    }

    @Test
    void testPairs() {
        // A leading zero byte followed by the values 0..17 produces "00" plus the
        // two-digit hex representation of the second byte.
        assertEncodesTo(0, 0, "0000");
        assertEncodesTo(0, 1, "0001");
        assertEncodesTo(0, 2, "0002");
        assertEncodesTo(0, 3, "0003");
        assertEncodesTo(0, 4, "0004");
        assertEncodesTo(0, 5, "0005");
        assertEncodesTo(0, 6, "0006");
        assertEncodesTo(0, 7, "0007");
        assertEncodesTo(0, 8, "0008");
        assertEncodesTo(0, 9, "0009");
        assertEncodesTo(0, 10, "000A");
        assertEncodesTo(0, 11, "000B");
        assertEncodesTo(0, 12, "000C");
        assertEncodesTo(0, 13, "000D");
        assertEncodesTo(0, 14, "000E");
        assertEncodesTo(0, 15, "000F");
        assertEncodesTo(0, 16, "0010");
        assertEncodesTo(0, 17, "0011");

        // Encoding then decoding any byte value must round-trip back to the original.
        for (int value = -128; value <= 127; value++) {
            final byte[] original = { (byte) value, (byte) value };
            final byte[] roundTripped = new Base16().decode(new Base16().encode(original));
            assertArrayEquals(original, roundTripped);
        }
    }
}

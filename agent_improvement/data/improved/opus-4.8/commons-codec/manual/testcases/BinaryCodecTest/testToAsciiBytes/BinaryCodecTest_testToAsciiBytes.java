package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link BinaryCodec#toAsciiBytes(byte[])}.
 *
 * <p>
 * {@code toAsciiBytes} turns each bit of the raw input into an ASCII '0' or '1'
 * character (returned as bytes). The output is laid out most-significant-bit
 * first, and {@code raw[0]} occupies the rightmost 8 characters while
 * {@code raw[1]} occupies the 8 characters to its left, and so on.
 * </p>
 */
public class BinaryCodecTest_testToAsciiBytes {

    /** Bit masks for the eight bits of a byte, from least to most significant. */
    private static final int BIT_0 = 0x01;
    private static final int BIT_1 = 0x02;
    private static final int BIT_2 = 0x04;
    private static final int BIT_3 = 0x08;
    private static final int BIT_4 = 0x10;
    private static final int BIT_5 = 0x20;
    private static final int BIT_6 = 0x40;
    private static final int BIT_7 = 0x80;

    /** All eight bits of a byte set (0xFF). */
    private static final int ALL_BITS = BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7;

    /**
     * Encodes the given raw byte values with {@link BinaryCodec#toAsciiBytes(byte[])}
     * and asserts the resulting ASCII string equals {@code expectedAscii}.
     *
     * @param expectedAscii the expected string of '0' and '1' characters
     * @param rawBytes      the raw byte values to encode (each truncated to a byte)
     */
    private static void assertToAsciiBytes(final String expectedAscii, final int... rawBytes) {
        final byte[] raw = new byte[rawBytes.length];
        for (int i = 0; i < rawBytes.length; i++) {
            raw[i] = (byte) rawBytes[i];
        }
        final String actualAscii = new String(BinaryCodec.toAsciiBytes(raw));
        assertEquals(expectedAscii, actualAscii);
    }

    @Test
    void testToAsciiBytes() {
        // A single raw byte: bits fill the 8-character output from the right.
        assertToAsciiBytes("00000000", 0);
        assertToAsciiBytes("00000001", BIT_0);
        assertToAsciiBytes("00000011", BIT_0 | BIT_1);
        assertToAsciiBytes("00000111", BIT_0 | BIT_1 | BIT_2);
        assertToAsciiBytes("00001111", BIT_0 | BIT_1 | BIT_2 | BIT_3);
        assertToAsciiBytes("00011111", BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4);
        assertToAsciiBytes("00111111", BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5);
        assertToAsciiBytes("01111111", BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6);
        assertToAsciiBytes("11111111", ALL_BITS);

        // Two raw bytes: raw[0] is the rightmost 8 characters; fill it bit by bit.
        assertToAsciiBytes("0000000000000000", 0, 0);
        assertToAsciiBytes("0000000000000001", BIT_0, 0);
        assertToAsciiBytes("0000000000000011", BIT_0 | BIT_1, 0);
        assertToAsciiBytes("0000000000000111", BIT_0 | BIT_1 | BIT_2, 0);
        assertToAsciiBytes("0000000000001111", BIT_0 | BIT_1 | BIT_2 | BIT_3, 0);
        assertToAsciiBytes("0000000000011111", BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4, 0);
        assertToAsciiBytes("0000000000111111", BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5, 0);
        assertToAsciiBytes("0000000001111111", BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6, 0);
        assertToAsciiBytes("0000000011111111", ALL_BITS, 0);

        // raw[0] stays full; now fill raw[1] (the leftmost 8 characters) bit by bit.
        assertToAsciiBytes("0000000111111111", ALL_BITS, BIT_0);
        assertToAsciiBytes("0000001111111111", ALL_BITS, BIT_0 | BIT_1);
        assertToAsciiBytes("0000011111111111", ALL_BITS, BIT_0 | BIT_1 | BIT_2);
        assertToAsciiBytes("0000111111111111", ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3);
        assertToAsciiBytes("0001111111111111", ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4);
        assertToAsciiBytes("0011111111111111", ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5);
        assertToAsciiBytes("0111111111111111", ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6);
        assertToAsciiBytes("1111111111111111", ALL_BITS, ALL_BITS);

        // A null input yields an empty array.
        assertEquals(0, BinaryCodec.toAsciiBytes((byte[]) null).length);
    }
}

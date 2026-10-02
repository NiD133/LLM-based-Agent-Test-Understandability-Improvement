package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link BinaryCodec#toAsciiChars(byte[])}, which renders each raw byte as
 * 8 ASCII '0'/'1' characters.
 *
 * <p>
 * Key behaviours exercised:
 * </p>
 * <ul>
 *   <li>Bit ordering within a byte: bit 0 (the least significant) maps to the
 *       right-most character, so a byte with its {@code n} lowest bits set
 *       produces {@code n} trailing '1's.</li>
 *   <li>Byte ordering within the array: {@code raw[0]} occupies the right-most
 *       8 characters and higher indices appear further to the left.</li>
 *   <li>A {@code null} input yields an empty result.</li>
 * </ul>
 */
public class BinaryCodecTest_testToAsciiChars {

    /**
     * Asserts that encoding the given raw bytes produces the expected string of
     * '0'/'1' characters. The raw bytes are listed in array order, i.e.
     * {@code raw[0]} first.
     */
    private static void assertToAsciiChars(final String expected, final byte... raw) {
        assertEquals(expected, new String(BinaryCodec.toAsciiChars(raw)));
    }

    /**
     * Returns a byte whose {@code count} lowest bits are set; e.g.
     * {@code lowBits(3) == 0b00000111} and {@code lowBits(8) == 0b11111111}.
     */
    private static byte lowBits(final int count) {
        return (byte) ((1 << count) - 1);
    }

    @Test
    void testToAsciiChars() {
        // A single raw byte: setting the lowest N bits yields N trailing '1's.
        assertToAsciiChars("00000000", lowBits(0));
        assertToAsciiChars("00000001", lowBits(1));
        assertToAsciiChars("00000011", lowBits(2));
        assertToAsciiChars("00000111", lowBits(3));
        assertToAsciiChars("00001111", lowBits(4));
        assertToAsciiChars("00011111", lowBits(5));
        assertToAsciiChars("00111111", lowBits(6));
        assertToAsciiChars("01111111", lowBits(7));
        assertToAsciiChars("11111111", lowBits(8));

        // Two raw bytes, high byte (raw[1]) clear: raw[0] fills the right-most 8 chars.
        assertToAsciiChars("0000000000000000", lowBits(0), lowBits(0));
        assertToAsciiChars("0000000000000001", lowBits(1), lowBits(0));
        assertToAsciiChars("0000000000000011", lowBits(2), lowBits(0));
        assertToAsciiChars("0000000000000111", lowBits(3), lowBits(0));
        assertToAsciiChars("0000000000001111", lowBits(4), lowBits(0));
        assertToAsciiChars("0000000000011111", lowBits(5), lowBits(0));
        assertToAsciiChars("0000000000111111", lowBits(6), lowBits(0));
        assertToAsciiChars("0000000001111111", lowBits(7), lowBits(0));
        assertToAsciiChars("0000000011111111", lowBits(8), lowBits(0));

        // Low byte (raw[0]) full, now filling the high byte (raw[1]) bit by bit.
        assertToAsciiChars("0000000111111111", lowBits(8), lowBits(1));
        assertToAsciiChars("0000001111111111", lowBits(8), lowBits(2));
        assertToAsciiChars("0000011111111111", lowBits(8), lowBits(3));
        assertToAsciiChars("0000111111111111", lowBits(8), lowBits(4));
        assertToAsciiChars("0001111111111111", lowBits(8), lowBits(5));
        assertToAsciiChars("0011111111111111", lowBits(8), lowBits(6));
        assertToAsciiChars("0111111111111111", lowBits(8), lowBits(7));
        assertToAsciiChars("1111111111111111", lowBits(8), lowBits(8));

        // A null input produces an empty result.
        assertEquals(0, BinaryCodec.toAsciiChars((byte[]) null).length);
    }
}

package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link BinaryCodec#toAsciiString(byte[])}, which renders raw binary data
 * as a String of '0' and '1' characters.
 *
 * <p>
 * Note on byte ordering: the byte at index 0 is rendered as the <em>right-most</em>
 * (least significant) group of 8 characters, so a two-byte array {@code {low, high}}
 * produces the String {@code "<high-byte><low-byte>"}.
 * </p>
 */
public class BinaryCodecTest_testToAsciiString {

    /**
     * Asserts that encoding a single raw byte yields the expected 8-character String.
     * <p>
     * The binary literal passed as {@code singleByte} is written to mirror the
     * {@code expected} String, making each case self-documenting.
     * </p>
     */
    private static void assertToAscii(final String expected, final int singleByte) {
        final byte[] raw = { (byte) singleByte };
        assertEquals(expected, BinaryCodec.toAsciiString(raw));
    }

    /**
     * Asserts that encoding two raw bytes yields the expected 16-character String.
     * <p>
     * Arguments are given in reading order: {@code highByte} becomes the left-most
     * 8 characters and {@code lowByte} the right-most 8 characters. Internally the
     * codec is handed the array {@code {lowByte, highByte}} (index 0 is least
     * significant).
     * </p>
     */
    private static void assertToAscii(final String expected, final int highByte, final int lowByte) {
        final byte[] raw = { (byte) lowByte, (byte) highByte };
        assertEquals(expected, BinaryCodec.toAsciiString(raw));
    }

    /**
     * Tests toAsciiString(byte[]) for single bytes, two bytes, and the null input.
     */
    @Test
    void testToAsciiString() {
        // A single raw byte: progressively raise the low bits 0..7.
        assertToAscii("00000000", 0b00000000);
        assertToAscii("00000001", 0b00000001);
        assertToAscii("00000011", 0b00000011);
        assertToAscii("00000111", 0b00000111);
        assertToAscii("00001111", 0b00001111);
        assertToAscii("00011111", 0b00011111);
        assertToAscii("00111111", 0b00111111);
        assertToAscii("01111111", 0b01111111);
        assertToAscii("11111111", 0b11111111);

        // Two raw bytes: vary the low byte while the high byte stays clear.
        assertToAscii("0000000000000000", 0b00000000, 0b00000000);
        assertToAscii("0000000000000001", 0b00000000, 0b00000001);
        assertToAscii("0000000000000011", 0b00000000, 0b00000011);
        assertToAscii("0000000000000111", 0b00000000, 0b00000111);
        assertToAscii("0000000000001111", 0b00000000, 0b00001111);
        assertToAscii("0000000000011111", 0b00000000, 0b00011111);
        assertToAscii("0000000000111111", 0b00000000, 0b00111111);
        assertToAscii("0000000001111111", 0b00000000, 0b01111111);
        assertToAscii("0000000011111111", 0b00000000, 0b11111111);

        // Two raw bytes: keep the low byte fully set and raise the high byte's bits.
        assertToAscii("0000000111111111", 0b00000001, 0b11111111);
        assertToAscii("0000001111111111", 0b00000011, 0b11111111);
        assertToAscii("0000011111111111", 0b00000111, 0b11111111);
        assertToAscii("0000111111111111", 0b00001111, 0b11111111);
        assertToAscii("0001111111111111", 0b00011111, 0b11111111);
        assertToAscii("0011111111111111", 0b00111111, 0b11111111);
        assertToAscii("0111111111111111", 0b01111111, 0b11111111);
        assertToAscii("1111111111111111", 0b11111111, 0b11111111);

        // A null input encodes to the empty String.
        assertEquals("", BinaryCodec.toAsciiString(null));
    }
}

package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link BinaryCodec#fromAscii(char[])}.
 *
 * <p>
 * {@code fromAscii} turns a char array of '0'/'1' characters into the raw bytes
 * those bits represent. Two rules drive the expectations below:
 * </p>
 * <ul>
 *   <li>Only complete groups of 8 characters are decoded; surplus leading
 *       characters that cannot fill a whole byte are ignored, so the result
 *       length is {@code input.length / 8}.</li>
 *   <li>The trailing 8 characters become the first byte of the result, the
 *       preceding 8 characters become the second byte, and so on (the result is
 *       ordered least-significant byte first).</li>
 * </ul>
 */
public class BinaryCodecTest_testFromAsciiCharArray {

    /**
     * Decodes {@code asciiBits} with {@link BinaryCodec#fromAscii(char[])} and
     * asserts the resulting raw bytes equal {@code expectedRaw}. The comparison
     * is done on the bytes wrapped in a {@link String}, matching the original
     * test's assertion style.
     *
     * @param expectedRaw the raw bytes the bit string should decode to.
     * @param asciiBits   a string of '0'/'1' characters to decode.
     */
    private static void assertDecodesTo(final byte[] expectedRaw, final String asciiBits) {
        final byte[] decoded = BinaryCodec.fromAscii(asciiBits.toCharArray());
        assertEquals(new String(expectedRaw), new String(decoded));
    }

    /*
     * Tests for byte[] fromAscii(char[])
     */
    @Test
    void testFromAsciiCharArray() {
        // A null or empty char array decodes to an empty byte array.
        assertEquals(0, BinaryCodec.fromAscii((char[]) null).length);
        assertEquals(0, BinaryCodec.fromAscii(new char[0]).length);

        // Surplus leading characters that do not complete a full byte are ignored.
        assertArrayEquals(new byte[0], BinaryCodec.fromAscii("1".toCharArray()));
        assertArrayEquals(new byte[] { 0 }, BinaryCodec.fromAscii("100000000".toCharArray()));
        assertArrayEquals(new byte[] { (byte) 0x80 }, BinaryCodec.fromAscii("010000000".toCharArray()));

        // One byte: the 8 bits map directly onto a single raw byte.
        assertDecodesTo(new byte[] { (byte) 0x00 }, "00000000");
        assertDecodesTo(new byte[] { (byte) 0x01 }, "00000001");
        assertDecodesTo(new byte[] { (byte) 0x03 }, "00000011");
        assertDecodesTo(new byte[] { (byte) 0x07 }, "00000111");
        assertDecodesTo(new byte[] { (byte) 0x0F }, "00001111");
        assertDecodesTo(new byte[] { (byte) 0x1F }, "00011111");
        assertDecodesTo(new byte[] { (byte) 0x3F }, "00111111");
        assertDecodesTo(new byte[] { (byte) 0x7F }, "01111111");
        assertDecodesTo(new byte[] { (byte) 0xFF }, "11111111");

        // Two bytes: the trailing 8 chars form byte[0], the leading 8 chars byte[1].
        assertDecodesTo(new byte[] { (byte) 0xFF, (byte) 0x00 }, "0000000011111111");
        assertDecodesTo(new byte[] { (byte) 0xFF, (byte) 0x01 }, "0000000111111111");
        assertDecodesTo(new byte[] { (byte) 0xFF, (byte) 0x03 }, "0000001111111111");
        assertDecodesTo(new byte[] { (byte) 0xFF, (byte) 0x07 }, "0000011111111111");
        assertDecodesTo(new byte[] { (byte) 0xFF, (byte) 0x0F }, "0000111111111111");
        assertDecodesTo(new byte[] { (byte) 0xFF, (byte) 0x1F }, "0001111111111111");
        assertDecodesTo(new byte[] { (byte) 0xFF, (byte) 0x3F }, "0011111111111111");
        assertDecodesTo(new byte[] { (byte) 0xFF, (byte) 0x7F }, "0111111111111111");
        assertDecodesTo(new byte[] { (byte) 0xFF, (byte) 0xFF }, "1111111111111111");

        // A null char array still decodes to an empty byte array.
        assertEquals(0, BinaryCodec.fromAscii((char[]) null).length);
    }
}

package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link BinaryCodec#decode(byte[])}.
 *
 * <p>
 * {@code decode(byte[])} reads a string of ASCII '0'/'1' characters (8 chars per
 * output byte) and returns the packed raw bytes. The decoder treats the right-most
 * group of 8 characters as the first output byte, so for multi-byte inputs the byte
 * order is reversed relative to the textual order.
 * </p>
 */
public class BinaryCodecTest_testDecodeByteArray {

    private static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;

    /** The binary codec under test. */
    private BinaryCodec instance;

    @BeforeEach
    void setUp() {
        this.instance = new BinaryCodec();
    }

    @AfterEach
    void tearDown() {
        this.instance = null;
    }

    /**
     * Decodes the given ASCII binary string and asserts that the resulting raw bytes
     * equal {@code expectedBytes}.
     *
     * @param binaryString the ASCII '0'/'1' input passed to {@code decode}.
     * @param expectedBytes the raw bytes the decoder is expected to produce.
     */
    private void assertDecodesTo(final String binaryString, final int... expectedBytes) {
        final byte[] expected = toByteArray(expectedBytes);
        final byte[] decoded = instance.decode(binaryString.getBytes(CHARSET_UTF8));
        assertArrayEquals(expected, decoded);
    }

    /** Narrows each int (e.g. a {@code 0x..} literal) into a byte for readable expected values. */
    private static byte[] toByteArray(final int... values) {
        final byte[] bytes = new byte[values.length];
        for (int i = 0; i < values.length; i++) {
            bytes[i] = (byte) values[i];
        }
        return bytes;
    }

    /**
     * Each 8-character binary string decodes to a single byte whose value is the
     * binary number it spells out.
     */
    @Test
    void testDecodeByteArray_singleByte() {
        assertDecodesTo("00000000", 0x00);
        assertDecodesTo("00000001", 0x01);
        assertDecodesTo("00000011", 0x03);
        assertDecodesTo("00000111", 0x07);
        assertDecodesTo("00001111", 0x0F);
        assertDecodesTo("00011111", 0x1F);
        assertDecodesTo("00111111", 0x3F);
        assertDecodesTo("01111111", 0x7F);
        assertDecodesTo("11111111", 0xFF);
    }

    /**
     * Each 16-character binary string decodes to two bytes. Because the decoder packs
     * the right-most 8 characters into the first output byte, the trailing "11111111"
     * always becomes byte[0] == 0xFF while the leading group fills byte[1].
     */
    @Test
    void testDecodeByteArray_twoBytes() {
        assertDecodesTo("0000000011111111", 0xFF, 0x00);
        assertDecodesTo("0000000111111111", 0xFF, 0x01);
        assertDecodesTo("0000001111111111", 0xFF, 0x03);
        assertDecodesTo("0000011111111111", 0xFF, 0x07);
        assertDecodesTo("0000111111111111", 0xFF, 0x0F);
        assertDecodesTo("0001111111111111", 0xFF, 0x1F);
        assertDecodesTo("0011111111111111", 0xFF, 0x3F);
        assertDecodesTo("0111111111111111", 0xFF, 0x7F);
        assertDecodesTo("1111111111111111", 0xFF, 0xFF);
    }
}

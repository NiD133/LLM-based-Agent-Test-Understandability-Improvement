package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BinaryCodecTest_testFromAsciiCharArray {

    private static final int BIT_0 = 0x01;
    private static final int BIT_1 = 0x02;
    private static final int BIT_2 = 0x04;
    private static final int BIT_3 = 0x08;
    private static final int BIT_4 = 0x10;
    private static final int BIT_5 = 0x20;
    private static final int BIT_6 = 0x40;
    private static final int BIT_7 = 0x80;
    private static final int ALL_BITS = BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7;

    @Test
    void testFromAsciiCharArray() {
        assertEquals(0, BinaryCodec.fromAscii((char[]) null).length);
        assertEquals(0, BinaryCodec.fromAscii(new char[0]).length);
        assertArrayEquals(new byte[0], BinaryCodec.fromAscii("1".toCharArray()));
        assertArrayEquals(new byte[] { 0 }, BinaryCodec.fromAscii("100000000".toCharArray()));
        assertArrayEquals(new byte[] { (byte) 0x80 }, BinaryCodec.fromAscii("010000000".toCharArray()));

        assertDecodedBytes("00000000", 0);
        assertDecodedBytes("00000001", BIT_0);
        assertDecodedBytes("00000011", BIT_0 | BIT_1);
        assertDecodedBytes("00000111", BIT_0 | BIT_1 | BIT_2);
        assertDecodedBytes("00001111", BIT_0 | BIT_1 | BIT_2 | BIT_3);
        assertDecodedBytes("00011111", BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4);
        assertDecodedBytes("00111111", BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5);
        assertDecodedBytes("01111111", BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6);
        assertDecodedBytes("11111111", ALL_BITS);

        assertDecodedBytes("0000000011111111", ALL_BITS, 0);
        assertDecodedBytes("0000000111111111", ALL_BITS, BIT_0);
        assertDecodedBytes("0000001111111111", ALL_BITS, BIT_0 | BIT_1);
        assertDecodedBytes("0000011111111111", ALL_BITS, BIT_0 | BIT_1 | BIT_2);
        assertDecodedBytes("0000111111111111", ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3);
        assertDecodedBytes("0001111111111111", ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4);
        assertDecodedBytes("0011111111111111", ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5);
        assertDecodedBytes("0111111111111111", ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6);
        assertDecodedBytes("1111111111111111", ALL_BITS, ALL_BITS);

        assertEquals(0, BinaryCodec.fromAscii((char[]) null).length);
    }

    private static void assertDecodedBytes(final String asciiBits, final int... expectedBytes) {
        final byte[] expected = toByteArray(expectedBytes);
        final byte[] decoded = BinaryCodec.fromAscii(asciiBits.toCharArray());
        assertEquals(new String(expected), new String(decoded));
    }

    private static byte[] toByteArray(final int... values) {
        final byte[] bytes = new byte[values.length];
        for (int i = 0; i < values.length; i++) {
            bytes[i] = (byte) values[i];
        }
        return bytes;
    }
}

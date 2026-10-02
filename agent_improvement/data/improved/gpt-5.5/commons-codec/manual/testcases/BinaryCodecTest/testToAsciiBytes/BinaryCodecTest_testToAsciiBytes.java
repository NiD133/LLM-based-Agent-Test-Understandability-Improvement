package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BinaryCodecTest_testToAsciiBytes {

    private static final int BIT_0 = 0x01;
    private static final int BIT_1 = 0x02;
    private static final int BIT_2 = 0x04;
    private static final int BIT_3 = 0x08;
    private static final int BIT_4 = 0x10;
    private static final int BIT_5 = 0x20;
    private static final int BIT_6 = 0x40;
    private static final int BIT_7 = 0x80;

    private static final byte LOW_BYTE_FULL = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);

    @Test
    void testToAsciiBytes() {
        assertAsciiEncoding("00000000", byteValue(0));
        assertAsciiEncoding("00000001", byteValue(BIT_0));
        assertAsciiEncoding("00000011", byteValue(BIT_0 | BIT_1));
        assertAsciiEncoding("00000111", byteValue(BIT_0 | BIT_1 | BIT_2));
        assertAsciiEncoding("00001111", byteValue(BIT_0 | BIT_1 | BIT_2 | BIT_3));
        assertAsciiEncoding("00011111", byteValue(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4));
        assertAsciiEncoding("00111111", byteValue(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5));
        assertAsciiEncoding("01111111", byteValue(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6));
        assertAsciiEncoding("11111111", byteValue(LOW_BYTE_FULL));

        assertAsciiEncoding("0000000000000000", twoBytes(0, 0));
        assertAsciiEncoding("0000000000000001", twoBytes(BIT_0, 0));
        assertAsciiEncoding("0000000000000011", twoBytes(BIT_0 | BIT_1, 0));
        assertAsciiEncoding("0000000000000111", twoBytes(BIT_0 | BIT_1 | BIT_2, 0));
        assertAsciiEncoding("0000000000001111", twoBytes(BIT_0 | BIT_1 | BIT_2 | BIT_3, 0));
        assertAsciiEncoding("0000000000011111", twoBytes(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4, 0));
        assertAsciiEncoding("0000000000111111", twoBytes(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5, 0));
        assertAsciiEncoding("0000000001111111", twoBytes(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6, 0));
        assertAsciiEncoding("0000000011111111", twoBytes(LOW_BYTE_FULL, 0));

        assertAsciiEncoding("0000000111111111", twoBytes(LOW_BYTE_FULL, BIT_0));
        assertAsciiEncoding("0000001111111111", twoBytes(LOW_BYTE_FULL, BIT_0 | BIT_1));
        assertAsciiEncoding("0000011111111111", twoBytes(LOW_BYTE_FULL, BIT_0 | BIT_1 | BIT_2));
        assertAsciiEncoding("0000111111111111", twoBytes(LOW_BYTE_FULL, BIT_0 | BIT_1 | BIT_2 | BIT_3));
        assertAsciiEncoding("0001111111111111", twoBytes(LOW_BYTE_FULL, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4));
        assertAsciiEncoding("0011111111111111", twoBytes(LOW_BYTE_FULL, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5));
        assertAsciiEncoding("0111111111111111", twoBytes(LOW_BYTE_FULL, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6));
        assertAsciiEncoding("1111111111111111", twoBytes(LOW_BYTE_FULL, LOW_BYTE_FULL));

        assertEquals(0, BinaryCodec.toAsciiBytes((byte[]) null).length);
    }

    private static void assertAsciiEncoding(final String expected, final byte[] rawBytes) {
        assertEquals(expected, new String(BinaryCodec.toAsciiBytes(rawBytes)));
    }

    private static byte[] byteValue(final int value) {
        return byteValue((byte) value);
    }

    private static byte[] byteValue(final byte value) {
        return new byte[] { value };
    }

    private static byte[] twoBytes(final int first, final int second) {
        return twoBytes((byte) first, (byte) second);
    }

    private static byte[] twoBytes(final byte first, final int second) {
        return twoBytes(first, (byte) second);
    }

    private static byte[] twoBytes(final byte first, final byte second) {
        return new byte[] { first, second };
    }
}

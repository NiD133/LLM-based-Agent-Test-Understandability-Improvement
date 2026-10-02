package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class BinaryCodecTest_testToAsciiChars {

    private static final int BIT_0 = 0x01;
    private static final int BIT_1 = 0x02;
    private static final int BIT_2 = 0x04;
    private static final int BIT_3 = 0x08;
    private static final int BIT_4 = 0x10;
    private static final int BIT_5 = 0x20;
    private static final int BIT_6 = 0x40;
    private static final int BIT_7 = 0x80;

    private static void assertToAsciiChars(byte[] raw, String expectedBinary) {
        assertEquals(expectedBinary, new String(BinaryCodec.toAsciiChars(raw)));
    }

    @Test
    void testToAsciiChars() {
        // Single byte: progressively set bits from LSB (bit 0) to MSB (bit 7)
        assertToAsciiChars(new byte[]{0},                                                              "00000000");
        assertToAsciiChars(new byte[]{(byte) BIT_0},                                                  "00000001");
        assertToAsciiChars(new byte[]{(byte) (BIT_0 | BIT_1)},                                        "00000011");
        assertToAsciiChars(new byte[]{(byte) (BIT_0 | BIT_1 | BIT_2)},                                "00000111");
        assertToAsciiChars(new byte[]{(byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3)},                        "00001111");
        assertToAsciiChars(new byte[]{(byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4)},               "00011111");
        assertToAsciiChars(new byte[]{(byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5)},       "00111111");
        assertToAsciiChars(new byte[]{(byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6)},                  "01111111");
        assertToAsciiChars(new byte[]{(byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7)},         "11111111");

        // Two bytes: first set bits only in the low byte (index 0), high byte (index 1) is zero
        assertToAsciiChars(new byte[]{0, 0},                                                           "0000000000000000");
        assertToAsciiChars(new byte[]{(byte) BIT_0, 0},                                               "0000000000000001");
        assertToAsciiChars(new byte[]{(byte) (BIT_0 | BIT_1), 0},                                     "0000000000000011");
        assertToAsciiChars(new byte[]{(byte) (BIT_0 | BIT_1 | BIT_2), 0},                             "0000000000000111");
        assertToAsciiChars(new byte[]{(byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3), 0},                    "0000000000001111");
        assertToAsciiChars(new byte[]{(byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4), 0},            "0000000000011111");
        assertToAsciiChars(new byte[]{(byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5), 0},   "0000000000111111");
        assertToAsciiChars(new byte[]{(byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6), 0},              "0000000001111111");
        assertToAsciiChars(new byte[]{(byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7), 0},     "0000000011111111");

        // Two bytes: low byte fully set, progressively set bits in the high byte (index 1)
        byte allBitsSet = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);
        assertToAsciiChars(new byte[]{allBitsSet, (byte) BIT_0},                                      "0000000111111111");
        assertToAsciiChars(new byte[]{allBitsSet, (byte) (BIT_0 | BIT_1)},                            "0000001111111111");
        assertToAsciiChars(new byte[]{allBitsSet, (byte) (BIT_0 | BIT_1 | BIT_2)},                   "0000011111111111");
        assertToAsciiChars(new byte[]{allBitsSet, (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3)},           "0000111111111111");
        assertToAsciiChars(new byte[]{allBitsSet, (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4)},  "0001111111111111");
        assertToAsciiChars(new byte[]{allBitsSet, (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5)},             "0011111111111111");
        assertToAsciiChars(new byte[]{allBitsSet, (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6)},    "0111111111111111");
        assertToAsciiChars(new byte[]{allBitsSet, allBitsSet},                                        "1111111111111111");

        // Null input returns an empty char array
        assertEquals(0, BinaryCodec.toAsciiChars((byte[]) null).length);
    }
}

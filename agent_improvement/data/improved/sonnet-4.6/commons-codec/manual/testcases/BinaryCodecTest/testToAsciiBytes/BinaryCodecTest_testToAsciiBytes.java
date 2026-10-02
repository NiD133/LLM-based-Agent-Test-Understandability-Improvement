package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

    // All eight bits set in a single byte (0xFF)
    private static final byte ALL_BITS_SET =
            (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);

    BinaryCodec instance;

    @BeforeEach
    void setUp() throws Exception {
        this.instance = new BinaryCodec();
    }

    @AfterEach
    void tearDown() throws Exception {
        this.instance = null;
    }

    /**
     * Asserts that toAsciiBytes encodes {@code raw} to the ASCII bit-string {@code expected}.
     */
    private void assertToAsciiBytes(final byte[] raw, final String expected) {
        assertEquals(expected, new String(BinaryCodec.toAsciiBytes(raw)));
    }

    @Test
    void testToAsciiBytes() {
        // --- Single byte: set bits one at a time from LSB (bit 0) to MSB (bit 7) ---
        assertToAsciiBytes(new byte[]{0},                                                              "00000000");
        assertToAsciiBytes(new byte[]{(byte)  BIT_0},                                                 "00000001");
        assertToAsciiBytes(new byte[]{(byte) (BIT_0 | BIT_1)},                                        "00000011");
        assertToAsciiBytes(new byte[]{(byte) (BIT_0 | BIT_1 | BIT_2)},                                "00000111");
        assertToAsciiBytes(new byte[]{(byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3)},                        "00001111");
        assertToAsciiBytes(new byte[]{(byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4)},               "00011111");
        assertToAsciiBytes(new byte[]{(byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5)},       "00111111");
        assertToAsciiBytes(new byte[]{(byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6)}, "01111111");
        assertToAsciiBytes(new byte[]{ALL_BITS_SET},                                                   "11111111");

        // --- Two bytes: vary the low byte (index 0) while the high byte (index 1) stays zero ---
        assertToAsciiBytes(new byte[]{0,                                                                  0}, "0000000000000000");
        assertToAsciiBytes(new byte[]{(byte)  BIT_0,                                                     0}, "0000000000000001");
        assertToAsciiBytes(new byte[]{(byte) (BIT_0 | BIT_1),                                            0}, "0000000000000011");
        assertToAsciiBytes(new byte[]{(byte) (BIT_0 | BIT_1 | BIT_2),                                    0}, "0000000000000111");
        assertToAsciiBytes(new byte[]{(byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3),                            0}, "0000000000001111");
        assertToAsciiBytes(new byte[]{(byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4),                   0}, "0000000000011111");
        assertToAsciiBytes(new byte[]{(byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5),           0}, "0000000000111111");
        assertToAsciiBytes(new byte[]{(byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6),   0}, "0000000001111111");
        assertToAsciiBytes(new byte[]{ALL_BITS_SET,                                                       0}, "0000000011111111");

        // --- Two bytes: low byte stays all-ones while the high byte (index 1) gains bits one at a time ---
        assertToAsciiBytes(new byte[]{ALL_BITS_SET, (byte)  BIT_0},                                                   "0000000111111111");
        assertToAsciiBytes(new byte[]{ALL_BITS_SET, (byte) (BIT_0 | BIT_1)},                                          "0000001111111111");
        assertToAsciiBytes(new byte[]{ALL_BITS_SET, (byte) (BIT_0 | BIT_1 | BIT_2)},                                  "0000011111111111");
        assertToAsciiBytes(new byte[]{ALL_BITS_SET, (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3)},                          "0000111111111111");
        assertToAsciiBytes(new byte[]{ALL_BITS_SET, (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4)},                 "0001111111111111");
        assertToAsciiBytes(new byte[]{ALL_BITS_SET, (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5)},        "0011111111111111");
        assertToAsciiBytes(new byte[]{ALL_BITS_SET, (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6)}, "0111111111111111");
        assertToAsciiBytes(new byte[]{ALL_BITS_SET, ALL_BITS_SET},                                                    "1111111111111111");

        // Null input must return an empty byte array (no NPE)
        assertEquals(0, BinaryCodec.toAsciiBytes((byte[]) null).length);
    }
}

package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BinaryCodecTest_testEncodeByteArray {

    private static final int BIT_0 = 0x01;
    private static final int BIT_1 = 0x02;
    private static final int BIT_2 = 0x04;
    private static final int BIT_3 = 0x08;
    private static final int BIT_4 = 0x10;
    private static final int BIT_5 = 0x20;
    private static final int BIT_6 = 0x40;
    private static final int BIT_7 = 0x80;

    /** All eight bits set (0xFF); needs a cast because 0xFF exceeds signed byte range. */
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

    /** Encodes {@code input} and asserts the resulting ASCII string equals {@code expectedBinary}. */
    private void assertEncode(final byte[] input, final String expectedBinary) {
        assertEquals(expectedBinary, new String(instance.encode(input)));
    }

    @Test
    void testEncodeByteArray() {
        // --- Single byte: progressively enable bits from LSB (bit 0) to MSB (bit 7) ---
        assertEncode(new byte[] { 0 },                                                        "00000000");
        assertEncode(new byte[] { BIT_0 },                                                    "00000001");
        assertEncode(new byte[] { BIT_0 | BIT_1 },                                            "00000011");
        assertEncode(new byte[] { BIT_0 | BIT_1 | BIT_2 },                                    "00000111");
        assertEncode(new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 },                            "00001111");
        assertEncode(new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 },                   "00011111");
        assertEncode(new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 },           "00111111");
        assertEncode(new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 },  "01111111");
        assertEncode(new byte[] { ALL_BITS_SET },                                              "11111111");

        // --- Two bytes: first byte (lower-order, bits[0]) varies; second byte (higher-order, bits[1]) is zero ---
        assertEncode(new byte[] { 0,                                                        0 }, "0000000000000000");
        assertEncode(new byte[] { BIT_0,                                                    0 }, "0000000000000001");
        assertEncode(new byte[] { BIT_0 | BIT_1,                                            0 }, "0000000000000011");
        assertEncode(new byte[] { BIT_0 | BIT_1 | BIT_2,                                    0 }, "0000000000000111");
        assertEncode(new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3,                            0 }, "0000000000001111");
        assertEncode(new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4,                   0 }, "0000000000011111");
        assertEncode(new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5,          0 }, "0000000000111111");
        assertEncode(new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6,  0 }, "0000000001111111");
        assertEncode(new byte[] { ALL_BITS_SET,                                             0 }, "0000000011111111");

        // --- Two bytes: first byte all bits set; progressively enable bits in the second (higher-order) byte ---
        assertEncode(new byte[] { ALL_BITS_SET, BIT_0 },                                                    "0000000111111111");
        assertEncode(new byte[] { ALL_BITS_SET, BIT_0 | BIT_1 },                                            "0000001111111111");
        assertEncode(new byte[] { ALL_BITS_SET, BIT_0 | BIT_1 | BIT_2 },                                    "0000011111111111");
        assertEncode(new byte[] { ALL_BITS_SET, BIT_0 | BIT_1 | BIT_2 | BIT_3 },                            "0000111111111111");
        assertEncode(new byte[] { ALL_BITS_SET, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 },                   "0001111111111111");
        assertEncode(new byte[] { ALL_BITS_SET, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 },           "0011111111111111");
        assertEncode(new byte[] { ALL_BITS_SET, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 },  "0111111111111111");
        assertEncode(new byte[] { ALL_BITS_SET, ALL_BITS_SET },                                              "1111111111111111");

        // Null input must yield an empty byte array
        assertEquals(0, instance.encode((byte[]) null).length);
    }
}

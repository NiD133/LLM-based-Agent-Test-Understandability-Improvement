package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BinaryCodecTest_testEncodeByteArray {

    private static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;

    private static final int BIT_0 = 0x01;
    private static final int BIT_1 = 0x02;
    private static final int BIT_2 = 0x04;
    private static final int BIT_3 = 0x08;
    private static final int BIT_4 = 0x10;
    private static final int BIT_5 = 0x20;
    private static final int BIT_6 = 0x40;
    private static final int BIT_7 = 0x80;

    /** All 8 bits set: 0b11111111. */
    private static final byte ALL_BITS_SET = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);

    BinaryCodec instance;

    void assertDecodeObject(final byte[] bits, final String encodeMe) throws DecoderException {
        byte[] decoded;
        decoded = (byte[]) instance.decode(encodeMe);
        assertEquals(new String(bits), new String(decoded));
        if (encodeMe == null) {
            decoded = instance.decode((byte[]) null);
        } else {
            decoded = (byte[]) instance.decode((Object) encodeMe.getBytes(CHARSET_UTF8));
        }
        assertEquals(new String(bits), new String(decoded));
        if (encodeMe == null) {
            decoded = (byte[]) instance.decode((char[]) null);
        } else {
            decoded = (byte[]) instance.decode(encodeMe.toCharArray());
        }
        assertEquals(new String(bits), new String(decoded));
    }

    @BeforeEach
    void setUp() throws Exception {
        this.instance = new BinaryCodec();
    }

    @AfterEach
    void tearDown() throws Exception {
        this.instance = null;
    }

    /** Encodes {@code bits} and asserts the binary string representation equals {@code expectedBinary}. */
    private void assertEncodes(byte[] bits, String expectedBinary) {
        assertEquals(expectedBinary, new String(instance.encode(bits)));
    }

    @Test
    void testEncodeByteArray() {
        // Single-byte input: bits set one at a time from LSB (bit 0) to MSB (bit 7)
        assertEncodes(new byte[1],                                                                          "00000000");
        assertEncodes(new byte[]{(byte)  BIT_0},                                                           "00000001");
        assertEncodes(new byte[]{(byte) (BIT_0 | BIT_1)},                                                  "00000011");
        assertEncodes(new byte[]{(byte) (BIT_0 | BIT_1 | BIT_2)},                                         "00000111");
        assertEncodes(new byte[]{(byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3)},                                 "00001111");
        assertEncodes(new byte[]{(byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4)},                        "00011111");
        assertEncodes(new byte[]{(byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5)},                "00111111");
        assertEncodes(new byte[]{(byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6)},       "01111111");
        assertEncodes(new byte[]{ALL_BITS_SET},                                                             "11111111");

        // Two-byte input, high byte (index 1) = 0x00: low byte (index 0) increments from 0 to all bits set
        assertEncodes(new byte[2],                                                                          "0000000000000000");
        assertEncodes(new byte[]{(byte)  BIT_0,                                                      0},   "0000000000000001");
        assertEncodes(new byte[]{(byte) (BIT_0 | BIT_1),                                            0},   "0000000000000011");
        assertEncodes(new byte[]{(byte) (BIT_0 | BIT_1 | BIT_2),                                   0},   "0000000000000111");
        assertEncodes(new byte[]{(byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3),                           0},   "0000000000001111");
        assertEncodes(new byte[]{(byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4),                  0},   "0000000000011111");
        assertEncodes(new byte[]{(byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5),         0},   "0000000000111111");
        assertEncodes(new byte[]{(byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6), 0},   "0000000001111111");
        assertEncodes(new byte[]{ALL_BITS_SET,                                                       0},   "0000000011111111");

        // Two-byte input, low byte (index 0) = ALL_BITS_SET: high byte (index 1) increments from bit 0 to all bits set
        assertEncodes(new byte[]{ALL_BITS_SET, (byte)  BIT_0},                                             "0000000111111111");
        assertEncodes(new byte[]{ALL_BITS_SET, (byte) (BIT_0 | BIT_1)},                                   "0000001111111111");
        assertEncodes(new byte[]{ALL_BITS_SET, (byte) (BIT_0 | BIT_1 | BIT_2)},                          "0000011111111111");
        assertEncodes(new byte[]{ALL_BITS_SET, (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3)},                  "0000111111111111");
        assertEncodes(new byte[]{ALL_BITS_SET, (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4)},          "0001111111111111");
        assertEncodes(new byte[]{ALL_BITS_SET, (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5)},  "0011111111111111");
        assertEncodes(new byte[]{ALL_BITS_SET, (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6)}, "0111111111111111");
        assertEncodes(new byte[]{ALL_BITS_SET, ALL_BITS_SET},                                              "1111111111111111");

        // Encoding null returns an empty array
        assertEquals(0, instance.encode((byte[]) null).length);
    }
}

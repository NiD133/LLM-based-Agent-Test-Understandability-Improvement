package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BinaryCodecTest_testToAsciiString {

    private static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;

    private static final int BIT_0 = 0x01;
    private static final int BIT_1 = 0x02;
    private static final int BIT_2 = 0x04;
    private static final int BIT_3 = 0x08;
    private static final int BIT_4 = 0x10;
    private static final int BIT_5 = 0x20;
    private static final int BIT_6 = 0x40;
    private static final int BIT_7 = 0x80;

    /** A byte with all 8 bits set (0xFF), used as a fixed low byte in two-byte tests. */
    private static final byte ALL_BITS_SET =
            (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);

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

    /** Asserts that toAsciiString produces the expected binary string for the given raw bytes. */
    private static void assertToAsciiString(byte[] rawBytes, String expectedBinaryString) {
        assertEquals(expectedBinaryString, BinaryCodec.toAsciiString(rawBytes));
    }

    /** Creates a one-element byte array with the given value. */
    private static byte[] singleByte(int value) {
        return new byte[] { (byte) value };
    }

    /**
     * Creates a two-element byte array where {@code lowByte} is stored at index 0 (least
     * significant in the encoded string) and {@code highByte} at index 1 (most significant).
     */
    private static byte[] twoBytes(int highByte, int lowByte) {
        return new byte[] { (byte) lowByte, (byte) highByte };
    }

    /**
     * Tests BinaryCodec.toAsciiString(byte[]) by incrementally setting bits in one- and
     * two-byte inputs and verifying the resulting ASCII '0'/'1' string.
     */
    @Test
    void testToAsciiString() {
        // Single byte: bits are set one at a time from LSB (bit 0) to MSB (bit 7)
        assertToAsciiString(singleByte(0),                                                               "00000000");
        assertToAsciiString(singleByte(BIT_0),                                                           "00000001");
        assertToAsciiString(singleByte(BIT_0 | BIT_1),                                                   "00000011");
        assertToAsciiString(singleByte(BIT_0 | BIT_1 | BIT_2),                                           "00000111");
        assertToAsciiString(singleByte(BIT_0 | BIT_1 | BIT_2 | BIT_3),                                  "00001111");
        assertToAsciiString(singleByte(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4),                         "00011111");
        assertToAsciiString(singleByte(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5),                "00111111");
        assertToAsciiString(singleByte(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6),       "01111111");
        assertToAsciiString(singleByte(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7), "11111111");

        // Two bytes, high byte zero: same incremental bit pattern in the low byte only
        assertToAsciiString(twoBytes(0, 0),                                                               "0000000000000000");
        assertToAsciiString(twoBytes(0, BIT_0),                                                           "0000000000000001");
        assertToAsciiString(twoBytes(0, BIT_0 | BIT_1),                                                   "0000000000000011");
        assertToAsciiString(twoBytes(0, BIT_0 | BIT_1 | BIT_2),                                           "0000000000000111");
        assertToAsciiString(twoBytes(0, BIT_0 | BIT_1 | BIT_2 | BIT_3),                                  "0000000000001111");
        assertToAsciiString(twoBytes(0, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4),                         "0000000000011111");
        assertToAsciiString(twoBytes(0, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5),                "0000000000111111");
        assertToAsciiString(twoBytes(0, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6),       "0000000001111111");
        assertToAsciiString(twoBytes(0, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7), "0000000011111111");

        // Two bytes, low byte fully set: incremental bit pattern added in the high byte
        assertToAsciiString(twoBytes(BIT_0,                                                               ALL_BITS_SET), "0000000111111111");
        assertToAsciiString(twoBytes(BIT_0 | BIT_1,                                                       ALL_BITS_SET), "0000001111111111");
        assertToAsciiString(twoBytes(BIT_0 | BIT_1 | BIT_2,                                               ALL_BITS_SET), "0000011111111111");
        assertToAsciiString(twoBytes(BIT_0 | BIT_1 | BIT_2 | BIT_3,                                      ALL_BITS_SET), "0000111111111111");
        assertToAsciiString(twoBytes(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4,                             ALL_BITS_SET), "0001111111111111");
        assertToAsciiString(twoBytes(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5,                    ALL_BITS_SET), "0011111111111111");
        assertToAsciiString(twoBytes(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6,           ALL_BITS_SET), "0111111111111111");
        assertToAsciiString(twoBytes(ALL_BITS_SET,                                                        ALL_BITS_SET), "1111111111111111");

        // Null input returns an empty string
        assertEquals("", BinaryCodec.toAsciiString(null));
    }
}

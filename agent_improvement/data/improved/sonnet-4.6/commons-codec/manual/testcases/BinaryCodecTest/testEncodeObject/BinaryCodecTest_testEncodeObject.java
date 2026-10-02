package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BinaryCodecTest_testEncodeObject {

    private static final int BIT_0 = 0x01;
    private static final int BIT_1 = 0x02;
    private static final int BIT_2 = 0x04;
    private static final int BIT_3 = 0x08;
    private static final int BIT_4 = 0x10;
    private static final int BIT_5 = 0x20;
    private static final int BIT_6 = 0x40;
    private static final int BIT_7 = 0x80;

    BinaryCodec instance;

    @BeforeEach
    void setUp() throws Exception {
        this.instance = new BinaryCodec();
    }

    @AfterEach
    void tearDown() throws Exception {
        this.instance = null;
    }

    /** Delegates to {@link BinaryCodec#encode(Object)} and returns the char[] result as a String. */
    private String encodeToString(byte[] raw) throws Exception {
        return new String((char[]) instance.encode((Object) raw));
    }

    @Test
    void testEncodeObject_singleByte() throws Exception {
        byte[] bits = new byte[1];

        assertEquals("00000000", encodeToString(bits));

        bits[0] = BIT_0;
        assertEquals("00000001", encodeToString(bits));

        bits[0] = BIT_0 | BIT_1;
        assertEquals("00000011", encodeToString(bits));

        bits[0] = BIT_0 | BIT_1 | BIT_2;
        assertEquals("00000111", encodeToString(bits));

        bits[0] = BIT_0 | BIT_1 | BIT_2 | BIT_3;
        assertEquals("00001111", encodeToString(bits));

        bits[0] = BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4;
        assertEquals("00011111", encodeToString(bits));

        bits[0] = BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5;
        assertEquals("00111111", encodeToString(bits));

        bits[0] = BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6;
        assertEquals("01111111", encodeToString(bits));

        bits[0] = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);
        assertEquals("11111111", encodeToString(bits));
    }

    @Test
    void testEncodeObject_twoBytes() throws Exception {
        final byte allBitsSet = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);
        byte[] bits = new byte[2];

        // Vary the low byte (bits[0]) while bits[1] remains zero
        assertEquals("0000000000000000", encodeToString(bits));

        bits[0] = BIT_0;
        assertEquals("0000000000000001", encodeToString(bits));

        bits[0] = BIT_0 | BIT_1;
        assertEquals("0000000000000011", encodeToString(bits));

        bits[0] = BIT_0 | BIT_1 | BIT_2;
        assertEquals("0000000000000111", encodeToString(bits));

        bits[0] = BIT_0 | BIT_1 | BIT_2 | BIT_3;
        assertEquals("0000000000001111", encodeToString(bits));

        bits[0] = BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4;
        assertEquals("0000000000011111", encodeToString(bits));

        bits[0] = BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5;
        assertEquals("0000000000111111", encodeToString(bits));

        bits[0] = BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6;
        assertEquals("0000000001111111", encodeToString(bits));

        bits[0] = allBitsSet;
        assertEquals("0000000011111111", encodeToString(bits));

        // Keep the low byte fully set and vary the high byte (bits[1])
        bits[1] = BIT_0;
        assertEquals("0000000111111111", encodeToString(bits));

        bits[1] = BIT_0 | BIT_1;
        assertEquals("0000001111111111", encodeToString(bits));

        bits[1] = BIT_0 | BIT_1 | BIT_2;
        assertEquals("0000011111111111", encodeToString(bits));

        bits[1] = BIT_0 | BIT_1 | BIT_2 | BIT_3;
        assertEquals("0000111111111111", encodeToString(bits));

        bits[1] = BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4;
        assertEquals("0001111111111111", encodeToString(bits));

        bits[1] = BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5;
        assertEquals("0011111111111111", encodeToString(bits));

        bits[1] = BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6;
        assertEquals("0111111111111111", encodeToString(bits));

        bits[1] = allBitsSet;
        assertEquals("1111111111111111", encodeToString(bits));
    }
}

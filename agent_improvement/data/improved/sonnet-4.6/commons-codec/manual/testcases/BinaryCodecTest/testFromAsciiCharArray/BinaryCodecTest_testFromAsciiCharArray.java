package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class BinaryCodecTest_testFromAsciiCharArray {

    /** Mask with bit zero-based index 0 raised. */
    private static final int BIT_0 = 0x01;

    /** Mask with bit zero-based index 1 raised. */
    private static final int BIT_1 = 0x02;

    /** Mask with bit zero-based index 2 raised. */
    private static final int BIT_2 = 0x04;

    /** Mask with bit zero-based index 3 raised. */
    private static final int BIT_3 = 0x08;

    /** Mask with bit zero-based index 4 raised. */
    private static final int BIT_4 = 0x10;

    /** Mask with bit zero-based index 5 raised. */
    private static final int BIT_5 = 0x20;

    /** Mask with bit zero-based index 6 raised. */
    private static final int BIT_6 = 0x40;

    /** Mask with bit zero-based index 7 raised. */
    private static final int BIT_7 = 0x80;

    /*
     * Tests for byte[] fromAscii(char[])
     */
    @Test
    void testFromAsciiCharArray() {
        // Null and empty inputs return an empty byte array
        assertEquals(0, BinaryCodec.fromAscii((char[]) null).length);
        assertEquals(0, BinaryCodec.fromAscii(new char[0]).length);

        // Input length not a multiple of 8 — extra leading chars are ignored
        assertArrayEquals(new byte[0], BinaryCodec.fromAscii("1".toCharArray()));
        assertArrayEquals(new byte[] { 0 }, BinaryCodec.fromAscii("100000000".toCharArray()));
        assertArrayEquals(new byte[] { (byte) 0x80 }, BinaryCodec.fromAscii("010000000".toCharArray()));

        // Single-byte decoding: verify each bit position is set correctly
        byte[] bits = new byte[1];
        byte[] decoded = BinaryCodec.fromAscii("00000000".toCharArray());
        assertEquals(new String(bits), new String(decoded));

        bits = new byte[1];
        bits[0] = BIT_0;
        decoded = BinaryCodec.fromAscii("00000001".toCharArray());
        assertEquals(new String(bits), new String(decoded));

        bits = new byte[1];
        bits[0] = BIT_0 | BIT_1;
        decoded = BinaryCodec.fromAscii("00000011".toCharArray());
        assertEquals(new String(bits), new String(decoded));

        bits = new byte[1];
        bits[0] = BIT_0 | BIT_1 | BIT_2;
        decoded = BinaryCodec.fromAscii("00000111".toCharArray());
        assertEquals(new String(bits), new String(decoded));

        bits = new byte[1];
        bits[0] = BIT_0 | BIT_1 | BIT_2 | BIT_3;
        decoded = BinaryCodec.fromAscii("00001111".toCharArray());
        assertEquals(new String(bits), new String(decoded));

        bits = new byte[1];
        bits[0] = BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4;
        decoded = BinaryCodec.fromAscii("00011111".toCharArray());
        assertEquals(new String(bits), new String(decoded));

        bits = new byte[1];
        bits[0] = BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5;
        decoded = BinaryCodec.fromAscii("00111111".toCharArray());
        assertEquals(new String(bits), new String(decoded));

        bits = new byte[1];
        bits[0] = BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6;
        decoded = BinaryCodec.fromAscii("01111111".toCharArray());
        assertEquals(new String(bits), new String(decoded));

        bits = new byte[1];
        bits[0] = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);
        decoded = BinaryCodec.fromAscii("11111111".toCharArray());
        assertEquals(new String(bits), new String(decoded));

        // Two-byte decoding: verify bit positions spread across the byte boundary
        bits = new byte[2];
        bits[0] = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);
        decoded = BinaryCodec.fromAscii("0000000011111111".toCharArray());
        assertEquals(new String(bits), new String(decoded));

        bits = new byte[2];
        bits[1] = BIT_0;
        bits[0] = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);
        decoded = BinaryCodec.fromAscii("0000000111111111".toCharArray());
        assertEquals(new String(bits), new String(decoded));

        bits = new byte[2];
        bits[1] = BIT_0 | BIT_1;
        bits[0] = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);
        decoded = BinaryCodec.fromAscii("0000001111111111".toCharArray());
        assertEquals(new String(bits), new String(decoded));

        bits = new byte[2];
        bits[1] = BIT_0 | BIT_1 | BIT_2;
        bits[0] = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);
        decoded = BinaryCodec.fromAscii("0000011111111111".toCharArray());
        assertEquals(new String(bits), new String(decoded));

        bits = new byte[2];
        bits[1] = BIT_0 | BIT_1 | BIT_2 | BIT_3;
        bits[0] = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);
        decoded = BinaryCodec.fromAscii("0000111111111111".toCharArray());
        assertEquals(new String(bits), new String(decoded));

        bits = new byte[2];
        bits[1] = BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4;
        bits[0] = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);
        decoded = BinaryCodec.fromAscii("0001111111111111".toCharArray());
        assertEquals(new String(bits), new String(decoded));

        bits = new byte[2];
        bits[1] = BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5;
        bits[0] = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);
        decoded = BinaryCodec.fromAscii("0011111111111111".toCharArray());
        assertEquals(new String(bits), new String(decoded));

        bits = new byte[2];
        bits[1] = BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6;
        bits[0] = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);
        decoded = BinaryCodec.fromAscii("0111111111111111".toCharArray());
        assertEquals(new String(bits), new String(decoded));

        bits = new byte[2];
        bits[1] = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);
        bits[0] = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);
        decoded = BinaryCodec.fromAscii("1111111111111111".toCharArray());
        assertEquals(new String(bits), new String(decoded));
    }
}

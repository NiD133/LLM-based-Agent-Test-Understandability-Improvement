package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link BinaryCodec#encode(byte[])}, which converts each byte of raw
 * binary data into eight ASCII '0'/'1' characters (most-significant bit first).
 */
public class BinaryCodecTest_testEncodeByteArray {

    /** Bit masks, indexed from the least-significant (bit 0) to most-significant (bit 7). */
    private static final int BIT_0 = 0x01;
    private static final int BIT_1 = 0x02;
    private static final int BIT_2 = 0x04;
    private static final int BIT_3 = 0x08;
    private static final int BIT_4 = 0x10;
    private static final int BIT_5 = 0x20;
    private static final int BIT_6 = 0x40;
    private static final int BIT_7 = 0x80;

    /** All eight bits of a byte raised (0xFF). */
    private static final byte ALL_BITS = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);

    /** The binary codec under test. */
    private BinaryCodec instance;

    @BeforeEach
    void setUp() {
        this.instance = new BinaryCodec();
    }

    /**
     * Encodes {@code raw} and asserts the produced ASCII bit string equals {@code expectedBits}.
     */
    private void assertEncodesTo(final String expectedBits, final byte[] raw) {
        assertEquals(expectedBits, new String(instance.encode(raw)));
    }

    @Test
    void testEncodeByteArray() {
        // A single raw byte encodes to eight ASCII bit characters.
        assertEncodesTo("00000000", new byte[] { 0 });
        assertEncodesTo("00000001", new byte[] { BIT_0 });
        assertEncodesTo("00000011", new byte[] { BIT_0 | BIT_1 });
        assertEncodesTo("00000111", new byte[] { BIT_0 | BIT_1 | BIT_2 });
        assertEncodesTo("00001111", new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 });
        assertEncodesTo("00011111", new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 });
        assertEncodesTo("00111111", new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 });
        assertEncodesTo("01111111", new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 });
        assertEncodesTo("11111111", new byte[] { ALL_BITS });

        // Two raw bytes encode to sixteen ASCII bit characters; index 0 is the low-order (rightmost) byte.
        assertEncodesTo("0000000000000000", new byte[] { 0, 0 });
        assertEncodesTo("0000000000000001", new byte[] { BIT_0, 0 });
        assertEncodesTo("0000000000000011", new byte[] { BIT_0 | BIT_1, 0 });
        assertEncodesTo("0000000000000111", new byte[] { BIT_0 | BIT_1 | BIT_2, 0 });
        assertEncodesTo("0000000000001111", new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3, 0 });
        assertEncodesTo("0000000000011111", new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4, 0 });
        assertEncodesTo("0000000000111111", new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5, 0 });
        assertEncodesTo("0000000001111111", new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6, 0 });
        assertEncodesTo("0000000011111111", new byte[] { ALL_BITS, 0 });

        // Low-order byte fully raised; now fill the high-order byte (index 1) bit by bit.
        assertEncodesTo("0000000111111111", new byte[] { ALL_BITS, BIT_0 });
        assertEncodesTo("0000001111111111", new byte[] { ALL_BITS, BIT_0 | BIT_1 });
        assertEncodesTo("0000011111111111", new byte[] { ALL_BITS, BIT_0 | BIT_1 | BIT_2 });
        assertEncodesTo("0000111111111111", new byte[] { ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 });
        assertEncodesTo("0001111111111111", new byte[] { ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 });
        assertEncodesTo("0011111111111111", new byte[] { ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 });
        assertEncodesTo("0111111111111111", new byte[] { ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 });
        assertEncodesTo("1111111111111111", new byte[] { ALL_BITS, ALL_BITS });

        // A null input encodes to an empty array.
        assertEquals(0, instance.encode((byte[]) null).length);
    }
}

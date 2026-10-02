package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BinaryCodecTest_testEncodeByteArray {

    /**
     * Mask with bit zero-based index 0 raised.
     */
    private static final int BIT_0 = 0x01;

    /**
     * Mask with bit zero-based index 1 raised.
     */
    private static final int BIT_1 = 0x02;

    /**
     * Mask with bit zero-based index 2 raised.
     */
    private static final int BIT_2 = 0x04;

    /**
     * Mask with bit zero-based index 3 raised.
     */
    private static final int BIT_3 = 0x08;

    /**
     * Mask with bit zero-based index 4 raised.
     */
    private static final int BIT_4 = 0x10;

    /**
     * Mask with bit zero-based index 5 raised.
     */
    private static final int BIT_5 = 0x20;

    /**
     * Mask with bit zero-based index 6 raised.
     */
    private static final int BIT_6 = 0x40;

    /**
     * Mask with bit zero-based index 7 raised.
     */
    private static final int BIT_7 = 0x80;

    private static final byte ALL_BITS = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);

    /**
     * An instance of the binary codec.
     */
    BinaryCodec instance;

    @BeforeEach
    void setUp() throws Exception {
        this.instance = new BinaryCodec();
    }

    @AfterEach
    void tearDown() throws Exception {
        this.instance = null;
    }

    /*
     * Tests for byte[] encode(byte[])
     */
    @Test
    void testEncodeByteArray() {
        assertSingleByteEncodings();
        assertTwoByteEncodingsWhenFirstByteChanges();
        assertTwoByteEncodingsWhenSecondByteChanges();
        assertEquals(0, instance.encode((byte[]) null).length);
    }

    private void assertSingleByteEncodings() {
        assertEncoding("00000000", new byte[1]);
        assertEncoding("00000001", new byte[] { BIT_0 });
        assertEncoding("00000011", new byte[] { BIT_0 | BIT_1 });
        assertEncoding("00000111", new byte[] { BIT_0 | BIT_1 | BIT_2 });
        assertEncoding("00001111", new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 });
        assertEncoding("00011111", new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 });
        assertEncoding("00111111", new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 });
        assertEncoding("01111111", new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 });
        assertEncoding("11111111", new byte[] { ALL_BITS });
    }

    private void assertTwoByteEncodingsWhenFirstByteChanges() {
        assertEncoding("0000000000000000", new byte[2]);
        assertEncoding("0000000000000001", new byte[] { BIT_0, 0 });
        assertEncoding("0000000000000011", new byte[] { BIT_0 | BIT_1, 0 });
        assertEncoding("0000000000000111", new byte[] { BIT_0 | BIT_1 | BIT_2, 0 });
        assertEncoding("0000000000001111", new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3, 0 });
        assertEncoding("0000000000011111", new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4, 0 });
        assertEncoding("0000000000111111", new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5, 0 });
        assertEncoding("0000000001111111", new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6, 0 });
        assertEncoding("0000000011111111", new byte[] { ALL_BITS, 0 });
    }

    private void assertTwoByteEncodingsWhenSecondByteChanges() {
        assertEncoding("0000000111111111", new byte[] { ALL_BITS, BIT_0 });
        assertEncoding("0000001111111111", new byte[] { ALL_BITS, BIT_0 | BIT_1 });
        assertEncoding("0000011111111111", new byte[] { ALL_BITS, BIT_0 | BIT_1 | BIT_2 });
        assertEncoding("0000111111111111", new byte[] { ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 });
        assertEncoding("0001111111111111", new byte[] { ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 });
        assertEncoding("0011111111111111", new byte[] { ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 });
        assertEncoding("0111111111111111", new byte[] { ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 });
        assertEncoding("1111111111111111", new byte[] { ALL_BITS, ALL_BITS });
    }

    private void assertEncoding(final String expected, final byte[] bits) {
        final String encoded = new String(instance.encode(bits));
        assertEquals(expected, encoded);
    }
}

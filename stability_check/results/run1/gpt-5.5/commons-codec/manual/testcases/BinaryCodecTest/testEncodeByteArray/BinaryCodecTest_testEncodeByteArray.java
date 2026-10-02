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

    private static final byte ALL_BITS_SET = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);

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
        assertTwoByteEncodingsForLowOrderByte();
        assertTwoByteEncodingsForHighOrderByte();
        assertEquals(0, instance.encode((byte[]) null).length);
    }

    private void assertSingleByteEncodings() {
        assertEncoded("00000000", new byte[1]);
        assertEncoded("00000001", new byte[] { BIT_0 });
        assertEncoded("00000011", new byte[] { BIT_0 | BIT_1 });
        assertEncoded("00000111", new byte[] { BIT_0 | BIT_1 | BIT_2 });
        assertEncoded("00001111", new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 });
        assertEncoded("00011111", new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 });
        assertEncoded("00111111", new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 });
        assertEncoded("01111111", new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 });
        assertEncoded("11111111", new byte[] { ALL_BITS_SET });
    }

    private void assertTwoByteEncodingsForLowOrderByte() {
        assertEncoded("0000000000000000", new byte[2]);
        assertEncoded("0000000000000001", new byte[] { BIT_0, 0 });
        assertEncoded("0000000000000011", new byte[] { BIT_0 | BIT_1, 0 });
        assertEncoded("0000000000000111", new byte[] { BIT_0 | BIT_1 | BIT_2, 0 });
        assertEncoded("0000000000001111", new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3, 0 });
        assertEncoded("0000000000011111", new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4, 0 });
        assertEncoded("0000000000111111", new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5, 0 });
        assertEncoded("0000000001111111", new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6, 0 });
        assertEncoded("0000000011111111", new byte[] { ALL_BITS_SET, 0 });
    }

    private void assertTwoByteEncodingsForHighOrderByte() {
        assertEncoded("0000000111111111", new byte[] { ALL_BITS_SET, BIT_0 });
        assertEncoded("0000001111111111", new byte[] { ALL_BITS_SET, BIT_0 | BIT_1 });
        assertEncoded("0000011111111111", new byte[] { ALL_BITS_SET, BIT_0 | BIT_1 | BIT_2 });
        assertEncoded("0000111111111111", new byte[] { ALL_BITS_SET, BIT_0 | BIT_1 | BIT_2 | BIT_3 });
        assertEncoded("0001111111111111", new byte[] { ALL_BITS_SET, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 });
        assertEncoded("0011111111111111", new byte[] { ALL_BITS_SET, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 });
        assertEncoded("0111111111111111", new byte[] { ALL_BITS_SET, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 });
        assertEncoded("1111111111111111", new byte[] { ALL_BITS_SET, ALL_BITS_SET });
    }

    private void assertEncoded(final String expected, final byte[] bits) {
        final String encoded = new String(instance.encode(bits));
        assertEquals(expected, encoded);
    }
}

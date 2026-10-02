package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BinaryCodecTest_testDecodeByteArray {

    private static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;

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

    private static final byte ALL_BITS = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);

    /** An instance of the binary codec. */
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
     * Tests for byte[] decode(byte[])
     */
    @Test
    void testDecodeByteArray() {
        assertSingleDecodedByte("00000000", (byte) 0);
        assertSingleDecodedByte("00000001", (byte) BIT_0);
        assertSingleDecodedByte("00000011", (byte) (BIT_0 | BIT_1));
        assertSingleDecodedByte("00000111", (byte) (BIT_0 | BIT_1 | BIT_2));
        assertSingleDecodedByte("00001111", (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3));
        assertSingleDecodedByte("00011111", (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4));
        assertSingleDecodedByte("00111111", (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5));
        assertSingleDecodedByte("01111111", (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6));
        assertSingleDecodedByte("11111111", ALL_BITS);

        assertTwoDecodedBytes("0000000011111111", ALL_BITS, (byte) 0);
        assertTwoDecodedBytes("0000000111111111", ALL_BITS, (byte) BIT_0);
        assertTwoDecodedBytes("0000001111111111", ALL_BITS, (byte) (BIT_0 | BIT_1));
        assertTwoDecodedBytes("0000011111111111", ALL_BITS, (byte) (BIT_0 | BIT_1 | BIT_2));
        assertTwoDecodedBytes("0000111111111111", ALL_BITS, (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3));
        assertTwoDecodedBytes("0001111111111111", ALL_BITS, (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4));
        assertTwoDecodedBytes("0011111111111111", ALL_BITS, (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5));
        assertTwoDecodedBytes("0111111111111111", ALL_BITS, (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6));
        assertTwoDecodedBytes("1111111111111111", ALL_BITS, ALL_BITS);
    }

    private void assertSingleDecodedByte(final String asciiBits, final byte expectedValue) {
        assertDecodedBytes(asciiBits, expectedValue);
    }

    private void assertTwoDecodedBytes(final String asciiBits, final byte leastSignificantByte, final byte mostSignificantByte) {
        assertDecodedBytes(asciiBits, leastSignificantByte, mostSignificantByte);
    }

    private void assertDecodedBytes(final String asciiBits, final byte... expectedBytes) {
        final byte[] decoded = instance.decode(asciiBits.getBytes(CHARSET_UTF8));
        assertEquals(new String(expectedBytes), new String(decoded));
    }
}

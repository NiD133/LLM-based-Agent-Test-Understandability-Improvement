package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BinaryCodecTest_testDecodeByteArray {

    private static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;

    private static final int BIT_0 = 0x01;
    private static final int BIT_1 = 0x02;
    private static final int BIT_2 = 0x04;
    private static final int BIT_3 = 0x08;
    private static final int BIT_4 = 0x10;
    private static final int BIT_5 = 0x20;
    private static final int BIT_6 = 0x40;
    private static final int BIT_7 = 0x80;

    private BinaryCodec instance;

    @BeforeEach
    void setUp() throws Exception {
        this.instance = new BinaryCodec();
    }

    @AfterEach
    void tearDown() throws Exception {
        this.instance = null;
    }

    private void assertDecodesTo(final String binaryAscii, final byte[] expectedBytes) {
        byte[] decoded = instance.decode(binaryAscii.getBytes(CHARSET_UTF8));
        assertArrayEquals(expectedBytes, decoded);
    }

    /*
     * Tests for byte[] decode(byte[])
     */
    @Test
    void testDecodeByteArray() {
        // Single-byte decoding: progressively set one more bit per case (LSB to MSB)
        assertDecodesTo("00000000", new byte[] { 0 });
        assertDecodesTo("00000001", new byte[] { (byte) BIT_0 });
        assertDecodesTo("00000011", new byte[] { (byte) (BIT_0 | BIT_1) });
        assertDecodesTo("00000111", new byte[] { (byte) (BIT_0 | BIT_1 | BIT_2) });
        assertDecodesTo("00001111", new byte[] { (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3) });
        assertDecodesTo("00011111", new byte[] { (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4) });
        assertDecodesTo("00111111", new byte[] { (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5) });
        assertDecodesTo("01111111", new byte[] { (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6) });
        assertDecodesTo("11111111", new byte[] { (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7) });

        // Two-byte decoding: the low byte (index 0) is always 0xFF; the high byte (index 1) gains one more bit each case
        final byte ALL_BITS_SET = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);
        assertDecodesTo("0000000011111111", new byte[] { ALL_BITS_SET, 0 });
        assertDecodesTo("0000000111111111", new byte[] { ALL_BITS_SET, (byte) BIT_0 });
        assertDecodesTo("0000001111111111", new byte[] { ALL_BITS_SET, (byte) (BIT_0 | BIT_1) });
        assertDecodesTo("0000011111111111", new byte[] { ALL_BITS_SET, (byte) (BIT_0 | BIT_1 | BIT_2) });
        assertDecodesTo("0000111111111111", new byte[] { ALL_BITS_SET, (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3) });
        assertDecodesTo("0001111111111111", new byte[] { ALL_BITS_SET, (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4) });
        assertDecodesTo("0011111111111111", new byte[] { ALL_BITS_SET, (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5) });
        assertDecodesTo("0111111111111111", new byte[] { ALL_BITS_SET, (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6) });
        assertDecodesTo("1111111111111111", new byte[] { ALL_BITS_SET, ALL_BITS_SET });
    }
}

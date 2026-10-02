package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BinaryCodecTest_testToByteArrayFromString {

    private static final int BIT_0 = 0x01;
    private static final int BIT_1 = 0x02;
    private static final int BIT_2 = 0x04;
    private static final int BIT_3 = 0x08;
    private static final int BIT_4 = 0x10;
    private static final int BIT_5 = 0x20;
    private static final int BIT_6 = 0x40;
    private static final int BIT_7 = 0x80;

    private static final byte ALL_BITS = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);

    private BinaryCodec instance;

    @BeforeEach
    void setUp() throws Exception {
        this.instance = new BinaryCodec();
    }

    @AfterEach
    void tearDown() throws Exception {
        this.instance = null;
    }

    @Test
    void testToByteArrayFromString() {
        assertToByteArray("00000000", new byte[] { 0 });
        assertToByteArray("00000001", new byte[] { BIT_0 });
        assertToByteArray("00000011", new byte[] { BIT_0 | BIT_1 });
        assertToByteArray("00000111", new byte[] { BIT_0 | BIT_1 | BIT_2 });
        assertToByteArray("00001111", new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 });
        assertToByteArray("00011111", new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 });
        assertToByteArray("00111111", new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 });
        assertToByteArray("01111111", new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 });
        assertToByteArray("11111111", new byte[] { ALL_BITS });

        assertToByteArray("0000000011111111", new byte[] { ALL_BITS, 0 });
        assertToByteArray("0000000111111111", new byte[] { ALL_BITS, BIT_0 });
        assertToByteArray("0000001111111111", new byte[] { ALL_BITS, BIT_0 | BIT_1 });
        assertToByteArray("0000011111111111", new byte[] { ALL_BITS, BIT_0 | BIT_1 | BIT_2 });
        assertToByteArray("0000111111111111", new byte[] { ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 });
        assertToByteArray("0001111111111111", new byte[] { ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 });
        assertToByteArray("0011111111111111", new byte[] { ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 });
        assertToByteArray("0111111111111111", new byte[] { ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 });
        assertToByteArray("1111111111111111", new byte[] { ALL_BITS, ALL_BITS });

        assertEquals(0, instance.toByteArray((String) null).length);
    }

    private void assertToByteArray(final String asciiBits, final byte[] expectedBits) {
        final byte[] decoded = instance.toByteArray(asciiBits);
        assertEquals(new String(expectedBits), new String(decoded));
    }
}

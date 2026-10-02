package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BinaryCodecTest_testFromAsciiByteArray {

    private static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;

    private static final int BIT_0 = 0x01;
    private static final int BIT_1 = 0x02;
    private static final int BIT_2 = 0x04;
    private static final int BIT_3 = 0x08;
    private static final int BIT_4 = 0x10;
    private static final int BIT_5 = 0x20;
    private static final int BIT_6 = 0x40;
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

    private void assertFromAsciiEquals(final byte[] expectedRawBytes, final String asciiBits) {
        final byte[] decoded = BinaryCodec.fromAscii(asciiBits.getBytes(CHARSET_UTF8));
        assertEquals(new String(expectedRawBytes), new String(decoded));
    }

    /*
     * Tests for byte[] fromAscii(byte[])
     */
    @Test
    void testFromAsciiByteArray() {
        assertEquals(0, BinaryCodec.fromAscii((byte[]) null).length);
        assertEquals(0, BinaryCodec.fromAscii(new byte[0]).length);
        assertArrayEquals(new byte[0], BinaryCodec.fromAscii("1".getBytes(CHARSET_UTF8)));
        assertArrayEquals(new byte[] { 0 }, BinaryCodec.fromAscii("100000000".getBytes(CHARSET_UTF8)));
        assertArrayEquals(new byte[] { (byte) 0x80 }, BinaryCodec.fromAscii("010000000".getBytes(CHARSET_UTF8)));

        // With a single raw binary
        assertFromAsciiEquals(new byte[1], "00000000");
        assertFromAsciiEquals(new byte[] { BIT_0 }, "00000001");
        assertFromAsciiEquals(new byte[] { BIT_0 | BIT_1 }, "00000011");
        assertFromAsciiEquals(new byte[] { BIT_0 | BIT_1 | BIT_2 }, "00000111");
        assertFromAsciiEquals(new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 }, "00001111");
        assertFromAsciiEquals(new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 }, "00011111");
        assertFromAsciiEquals(new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 }, "00111111");
        assertFromAsciiEquals(new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 }, "01111111");
        assertFromAsciiEquals(new byte[] { ALL_BITS }, "11111111");

        // With two raw binaries
        assertFromAsciiEquals(new byte[] { ALL_BITS, 0 }, "0000000011111111");
        assertFromAsciiEquals(new byte[] { ALL_BITS, BIT_0 }, "0000000111111111");
        assertFromAsciiEquals(new byte[] { ALL_BITS, BIT_0 | BIT_1 }, "0000001111111111");
        assertFromAsciiEquals(new byte[] { ALL_BITS, BIT_0 | BIT_1 | BIT_2 }, "0000011111111111");
        assertFromAsciiEquals(new byte[] { ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 }, "0000111111111111");
        assertFromAsciiEquals(new byte[] { ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 }, "0001111111111111");
        assertFromAsciiEquals(new byte[] { ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 },
                "0011111111111111");
        assertFromAsciiEquals(new byte[] { ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 },
                "0111111111111111");
        assertFromAsciiEquals(new byte[] { ALL_BITS, ALL_BITS }, "1111111111111111");

        assertEquals(0, BinaryCodec.fromAscii((byte[]) null).length);
    }
}

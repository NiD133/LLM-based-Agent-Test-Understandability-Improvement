package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link BinaryCodec#encode(byte[])}, which renders each raw byte as its
 * eight-character string of '0' and '1' bits.
 */
public class BinaryCodecTest_testEncodeByteArray {

    // Bit masks, one per zero-based bit index within a byte.
    private static final int BIT_0 = 0x01;
    private static final int BIT_1 = 0x02;
    private static final int BIT_2 = 0x04;
    private static final int BIT_3 = 0x08;
    private static final int BIT_4 = 0x10;
    private static final int BIT_5 = 0x20;
    private static final int BIT_6 = 0x40;
    private static final int BIT_7 = 0x80;

    /** All eight bits of a byte raised. */
    private static final int ALL_BITS = BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7;

    private BinaryCodec instance;

    @BeforeEach
    void setUp() {
        this.instance = new BinaryCodec();
    }

    @AfterEach
    void tearDown() {
        this.instance = null;
    }

    /**
     * Encodes a single raw byte and asserts on its eight-character binary string.
     *
     * @param rawByte  the raw byte value to encode
     * @param expected the expected string of eight '0'/'1' characters
     */
    private void assertEncodes(final int rawByte, final String expected) {
        final byte[] raw = { (byte) rawByte };
        assertEquals(expected, new String(instance.encode(raw)));
    }

    /**
     * Encodes two raw bytes and asserts on their sixteen-character binary string.
     * <p>
     * The codec renders {@code byte0} as the rightmost (least significant) eight
     * characters and {@code byte1} as the leftmost eight characters.
     *
     * @param byte0    the raw value stored at index 0 (low-order byte)
     * @param byte1    the raw value stored at index 1 (high-order byte)
     * @param expected the expected string of sixteen '0'/'1' characters
     */
    private void assertEncodes(final int byte0, final int byte1, final String expected) {
        final byte[] raw = { (byte) byte0, (byte) byte1 };
        assertEquals(expected, new String(instance.encode(raw)));
    }

    @Test
    void testEncodeByteArray() {
        // A single raw byte: progressively raise bits from index 0 upward.
        assertEncodes(0, "00000000");
        assertEncodes(BIT_0, "00000001");
        assertEncodes(BIT_0 | BIT_1, "00000011");
        assertEncodes(BIT_0 | BIT_1 | BIT_2, "00000111");
        assertEncodes(BIT_0 | BIT_1 | BIT_2 | BIT_3, "00001111");
        assertEncodes(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4, "00011111");
        assertEncodes(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5, "00111111");
        assertEncodes(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6, "01111111");
        assertEncodes(ALL_BITS, "11111111");

        // Two raw bytes: raise bits in the low-order byte (index 0) only.
        assertEncodes(0, 0, "0000000000000000");
        assertEncodes(BIT_0, 0, "0000000000000001");
        assertEncodes(BIT_0 | BIT_1, 0, "0000000000000011");
        assertEncodes(BIT_0 | BIT_1 | BIT_2, 0, "0000000000000111");
        assertEncodes(BIT_0 | BIT_1 | BIT_2 | BIT_3, 0, "0000000000001111");
        assertEncodes(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4, 0, "0000000000011111");
        assertEncodes(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5, 0, "0000000000111111");
        assertEncodes(BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6, 0, "0000000001111111");
        assertEncodes(ALL_BITS, 0, "0000000011111111");

        // Two raw bytes: low-order byte full, progressively raise the high-order byte (index 1).
        assertEncodes(ALL_BITS, BIT_0, "0000000111111111");
        assertEncodes(ALL_BITS, BIT_0 | BIT_1, "0000001111111111");
        assertEncodes(ALL_BITS, BIT_0 | BIT_1 | BIT_2, "0000011111111111");
        assertEncodes(ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3, "0000111111111111");
        assertEncodes(ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4, "0001111111111111");
        assertEncodes(ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5, "0011111111111111");
        assertEncodes(ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6, "0111111111111111");
        assertEncodes(ALL_BITS, ALL_BITS, "1111111111111111");

        // A null input encodes to an empty array.
        assertEquals(0, instance.encode((byte[]) null).length);
    }
}

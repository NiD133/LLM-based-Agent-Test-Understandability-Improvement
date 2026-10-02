package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link BinaryCodec#encode(Object)}.
 *
 * <p>
 * {@code encode(Object)} expects a {@code byte[]} of raw binary data and returns a {@code char[]}
 * holding the ASCII '0'/'1' representation of every bit. The codec emits eight characters per byte,
 * most-significant bit first, and prints the bytes of the array from the last index down to the
 * first. These tests feed in known byte arrays and confirm the resulting bit string.
 * </p>
 */
public class BinaryCodecTest_testEncodeObject {

    /** Byte masks for bits 0 (least significant) through 7 (most significant). */
    private static final byte BIT_0 = 0x01;
    private static final byte BIT_1 = 0x02;
    private static final byte BIT_2 = 0x04;
    private static final byte BIT_3 = 0x08;
    private static final byte BIT_4 = 0x10;
    private static final byte BIT_5 = 0x20;
    private static final byte BIT_6 = 0x40;
    private static final byte BIT_7 = (byte) 0x80;

    /** A single byte with every bit set (0xFF). */
    private static final byte ALL_BITS =
            (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);

    private BinaryCodec instance;

    @BeforeEach
    void setUp() {
        instance = new BinaryCodec();
    }

    /**
     * Encodes the given raw bytes through {@code encode(Object)} and returns the resulting
     * bit string.
     */
    private String encode(final byte... rawBytes) throws Exception {
        return new String((char[]) instance.encode((Object) rawBytes));
    }

    @Test
    void encodesSingleByteOneBitAtATime() throws Exception {
        // Starting from all zeros, raise one more low-order bit each time.
        assertEquals("00000000", encode((byte) 0));
        assertEquals("00000001", encode((byte) BIT_0));
        assertEquals("00000011", encode((byte) (BIT_0 | BIT_1)));
        assertEquals("00000111", encode((byte) (BIT_0 | BIT_1 | BIT_2)));
        assertEquals("00001111", encode((byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3)));
        assertEquals("00011111", encode((byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4)));
        assertEquals("00111111", encode((byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5)));
        assertEquals("01111111",
                encode((byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6)));
        assertEquals("11111111", encode(ALL_BITS));
    }

    @Test
    void encodesLowByteOfTwoByteArray() throws Exception {
        // The high byte (index 1) stays zero; bits accumulate in the low byte (index 0),
        // which the codec prints on the right.
        assertEquals("0000000000000000", encode((byte) 0, (byte) 0));
        assertEquals("0000000000000001", encode(BIT_0, (byte) 0));
        assertEquals("0000000000000011", encode((byte) (BIT_0 | BIT_1), (byte) 0));
        assertEquals("0000000000000111", encode((byte) (BIT_0 | BIT_1 | BIT_2), (byte) 0));
        assertEquals("0000000000001111", encode((byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3), (byte) 0));
        assertEquals("0000000000011111",
                encode((byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4), (byte) 0));
        assertEquals("0000000000111111",
                encode((byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5), (byte) 0));
        assertEquals("0000000001111111",
                encode((byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6), (byte) 0));
        assertEquals("0000000011111111", encode(ALL_BITS, (byte) 0));
    }

    @Test
    void encodesHighByteOfTwoByteArray() throws Exception {
        // The low byte (index 0) stays fully set; bits accumulate in the high byte (index 1),
        // which the codec prints on the left.
        assertEquals("0000000111111111", encode(ALL_BITS, BIT_0));
        assertEquals("0000001111111111", encode(ALL_BITS, (byte) (BIT_0 | BIT_1)));
        assertEquals("0000011111111111", encode(ALL_BITS, (byte) (BIT_0 | BIT_1 | BIT_2)));
        assertEquals("0000111111111111", encode(ALL_BITS, (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3)));
        assertEquals("0001111111111111",
                encode(ALL_BITS, (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4)));
        assertEquals("0011111111111111",
                encode(ALL_BITS, (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5)));
        assertEquals("0111111111111111",
                encode(ALL_BITS, (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6)));
        assertEquals("1111111111111111", encode(ALL_BITS, ALL_BITS));
    }
}

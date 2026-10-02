package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link BinaryCodec#fromAscii(byte[])}.
 *
 * <p>{@code fromAscii} reads a byte array of ASCII {@code '0'}/{@code '1'} characters and
 * packs every full group of 8 characters into one raw byte. The characters are consumed
 * from right to left, so the right-most 8 characters become the first raw byte
 * ({@code raw[0]}). Any leading characters that do not complete a group of 8 are
 * discarded, and the {@code '1'} in each group maps to the bit position counted from the
 * right of that group.</p>
 */
public class BinaryCodecTest_testFromAsciiByteArray {

    private static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;

    // Single-bit masks, named by their zero-based position within a byte (BIT_0 is the
    // least significant bit). These mirror the masks used internally by BinaryCodec so the
    // expected bytes below can be read as "which bits are set".
    private static final int BIT_0 = 0x01;
    private static final int BIT_1 = 0x02;
    private static final int BIT_2 = 0x04;
    private static final int BIT_3 = 0x08;
    private static final int BIT_4 = 0x10;
    private static final int BIT_5 = 0x20;
    private static final int BIT_6 = 0x40;
    private static final int BIT_7 = 0x80;

    /** A byte with every bit set (0xFF). */
    private static final byte ALL_BITS = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);

    /**
     * Decodes {@code asciiBits} (encoded as UTF-8 bytes) via {@link BinaryCodec#fromAscii(byte[])}
     * and asserts that the raw result matches {@code expectedRaw}.
     *
     * <p>The arrays are compared as Strings, exactly as the original test did, so that a
     * mismatch reports the actual byte contents.</p>
     */
    private static void assertFromAscii(final byte[] expectedRaw, final String asciiBits) {
        final byte[] decoded = BinaryCodec.fromAscii(asciiBits.getBytes(CHARSET_UTF8));
        assertEquals(new String(expectedRaw), new String(decoded));
    }

    @Test
    void testFromAsciiByteArray() {
        // null and empty input both decode to an empty array.
        assertEquals(0, BinaryCodec.fromAscii((byte[]) null).length);
        assertEquals(0, BinaryCodec.fromAscii(new byte[0]).length);

        // Fewer than 8 characters cannot fill a byte, so nothing is produced.
        assertArrayEquals(new byte[0], BinaryCodec.fromAscii("1".getBytes(CHARSET_UTF8)));

        // Only the last 8 characters count; any extra leading characters are ignored.
        assertArrayEquals(new byte[] { 0 }, BinaryCodec.fromAscii("100000000".getBytes(CHARSET_UTF8)));
        assertArrayEquals(new byte[] { (byte) 0x80 }, BinaryCodec.fromAscii("010000000".getBytes(CHARSET_UTF8)));

        // One byte: turn on progressively more low-order bits, from none through all 8.
        assertFromAscii(new byte[] { 0 }, "00000000");
        assertFromAscii(new byte[] { BIT_0 }, "00000001");
        assertFromAscii(new byte[] { BIT_0 | BIT_1 }, "00000011");
        assertFromAscii(new byte[] { BIT_0 | BIT_1 | BIT_2 }, "00000111");
        assertFromAscii(new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 }, "00001111");
        assertFromAscii(new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 }, "00011111");
        assertFromAscii(new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 }, "00111111");
        assertFromAscii(new byte[] { BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 }, "01111111");
        assertFromAscii(new byte[] { ALL_BITS }, "11111111");

        // Two bytes: the right-most 8 characters fill raw[0], the left-most 8 fill raw[1].
        // Here raw[0] is always all-ones ("11111111") while raw[1] gains more low-order bits.
        assertFromAscii(new byte[] { ALL_BITS, 0 }, "0000000011111111");
        assertFromAscii(new byte[] { ALL_BITS, BIT_0 }, "0000000111111111");
        assertFromAscii(new byte[] { ALL_BITS, BIT_0 | BIT_1 }, "0000001111111111");
        assertFromAscii(new byte[] { ALL_BITS, BIT_0 | BIT_1 | BIT_2 }, "0000011111111111");
        assertFromAscii(new byte[] { ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 }, "0000111111111111");
        assertFromAscii(new byte[] { ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 }, "0001111111111111");
        assertFromAscii(new byte[] { ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 }, "0011111111111111");
        assertFromAscii(new byte[] { ALL_BITS, BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 }, "0111111111111111");
        assertFromAscii(new byte[] { ALL_BITS, ALL_BITS }, "1111111111111111");

        // Sanity check again that null input yields an empty array.
        assertEquals(0, BinaryCodec.fromAscii((byte[]) null).length);
    }
}

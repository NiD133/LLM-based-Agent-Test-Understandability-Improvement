package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

public class BinaryCodecTest_testFromAsciiByteArray {

    private static final int BIT_0 = 0x01;
    private static final int BIT_1 = 0x02;
    private static final int BIT_2 = 0x04;
    private static final int BIT_3 = 0x08;
    private static final int BIT_4 = 0x10;
    private static final int BIT_5 = 0x20;
    private static final int BIT_6 = 0x40;
    private static final int BIT_7 = 0x80;

    private static byte[] asciiBytes(final String s) {
        return s.getBytes(StandardCharsets.UTF_8);
    }

    private static void assertFromAscii(final byte[] expectedBits, final String asciiInput) {
        final byte[] decoded = BinaryCodec.fromAscii(asciiBytes(asciiInput));
        assertEquals(new String(expectedBits), new String(decoded));
    }

    @Test
    void testFromAsciiByteArray() {
        // Null and empty inputs return empty arrays
        assertEquals(0, BinaryCodec.fromAscii((byte[]) null).length);
        assertEquals(0, BinaryCodec.fromAscii(new byte[0]).length);

        // Inputs shorter than 8 bits produce no complete byte (empty result)
        assertArrayEquals(new byte[0], BinaryCodec.fromAscii(asciiBytes("1")));

        // 9-bit input: only the rightmost 8 bits form one byte; leading extra bits are dropped
        assertArrayEquals(new byte[] { 0 },          BinaryCodec.fromAscii(asciiBytes("100000000")));
        assertArrayEquals(new byte[] { (byte) 0x80 }, BinaryCodec.fromAscii(asciiBytes("010000000")));

        // Single-byte decoding: progressively set bits from LSB (bit 0) up to MSB (bit 7)
        assertFromAscii(new byte[] { 0 },                                                                    "00000000");
        assertFromAscii(new byte[] { (byte) BIT_0 },                                                        "00000001");
        assertFromAscii(new byte[] { (byte) (BIT_0 | BIT_1) },                                              "00000011");
        assertFromAscii(new byte[] { (byte) (BIT_0 | BIT_1 | BIT_2) },                                     "00000111");
        assertFromAscii(new byte[] { (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3) },                             "00001111");
        assertFromAscii(new byte[] { (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4) },                    "00011111");
        assertFromAscii(new byte[] { (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5) },            "00111111");
        assertFromAscii(new byte[] { (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6) },   "01111111");
        assertFromAscii(new byte[] { (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7) }, "11111111");

        // Two-byte decoding: low byte (index 0) is always all-ones; high byte (index 1) grows from LSB to MSB
        final byte allOnes = (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6 | BIT_7);
        assertFromAscii(new byte[] { allOnes, 0 },                                                                    "0000000011111111");
        assertFromAscii(new byte[] { allOnes, (byte) BIT_0 },                                                        "0000000111111111");
        assertFromAscii(new byte[] { allOnes, (byte) (BIT_0 | BIT_1) },                                              "0000001111111111");
        assertFromAscii(new byte[] { allOnes, (byte) (BIT_0 | BIT_1 | BIT_2) },                                     "0000011111111111");
        assertFromAscii(new byte[] { allOnes, (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3) },                             "0000111111111111");
        assertFromAscii(new byte[] { allOnes, (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4) },                    "0001111111111111");
        assertFromAscii(new byte[] { allOnes, (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5) },            "0011111111111111");
        assertFromAscii(new byte[] { allOnes, (byte) (BIT_0 | BIT_1 | BIT_2 | BIT_3 | BIT_4 | BIT_5 | BIT_6) },   "0111111111111111");
        assertFromAscii(new byte[] { allOnes, allOnes },                                                              "1111111111111111");
    }
}

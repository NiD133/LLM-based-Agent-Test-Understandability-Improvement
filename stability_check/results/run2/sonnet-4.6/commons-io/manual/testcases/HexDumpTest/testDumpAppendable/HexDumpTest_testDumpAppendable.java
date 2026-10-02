package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class HexDumpTest_testDumpAppendable {

    private static final String NL = System.lineSeparator();

    /** Expected output when dumping all 256 bytes (0x00–0xFF) from offset 0. */
    private static final String EXPECTED_FULL_DUMP =
        "00000000 00 01 02 03 04 05 06 07 08 09 0A 0B 0C 0D 0E 0F ................" + NL +
        "00000010 10 11 12 13 14 15 16 17 18 19 1A 1B 1C 1D 1E 1F ................" + NL +
        "00000020 20 21 22 23 24 25 26 27 28 29 2A 2B 2C 2D 2E 2F  !\"#$%&'()*+,-./" + NL +
        "00000030 30 31 32 33 34 35 36 37 38 39 3A 3B 3C 3D 3E 3F 0123456789:;<=>?" + NL +
        "00000040 40 41 42 43 44 45 46 47 48 49 4A 4B 4C 4D 4E 4F @ABCDEFGHIJKLMNO" + NL +
        "00000050 50 51 52 53 54 55 56 57 58 59 5A 5B 5C 5D 5E 5F PQRSTUVWXYZ[\\]^_" + NL +
        "00000060 60 61 62 63 64 65 66 67 68 69 6A 6B 6C 6D 6E 6F `abcdefghijklmno" + NL +
        "00000070 70 71 72 73 74 75 76 77 78 79 7A 7B 7C 7D 7E 7F pqrstuvwxyz{|}~." + NL +
        "00000080 80 81 82 83 84 85 86 87 88 89 8A 8B 8C 8D 8E 8F ................" + NL +
        "00000090 90 91 92 93 94 95 96 97 98 99 9A 9B 9C 9D 9E 9F ................" + NL +
        "000000A0 A0 A1 A2 A3 A4 A5 A6 A7 A8 A9 AA AB AC AD AE AF ................" + NL +
        "000000B0 B0 B1 B2 B3 B4 B5 B6 B7 B8 B9 BA BB BC BD BE BF ................" + NL +
        "000000C0 C0 C1 C2 C3 C4 C5 C6 C7 C8 C9 CA CB CC CD CE CF ................" + NL +
        "000000D0 D0 D1 D2 D3 D4 D5 D6 D7 D8 D9 DA DB DC DD DE DF ................" + NL +
        "000000E0 E0 E1 E2 E3 E4 E5 E6 E7 E8 E9 EA EB EC ED EE EF ................" + NL +
        "000000F0 F0 F1 F2 F3 F4 F5 F6 F7 F8 F9 FA FB FC FD FE FF ................" + NL;

    @Test
    void testDumpAppendable() throws IOException {
        // Build a test array containing every byte value from 0x00 to 0xFF
        final byte[] testArray = new byte[256];
        for (int i = 0; i < 256; i++) {
            testArray[i] = (byte) i;
        }

        // Dump the entire array; all 16 rows should appear at display offset 0
        StringBuilder out = new StringBuilder();
        HexDump.dump(testArray, out);
        assertEquals(EXPECTED_FULL_DUMP, out.toString());

        // Dump 32 bytes starting at array index 0x28, using a non-zero display offset.
        // The display offset (0x10000000) is added to the index so each row header
        // shows the position within the larger logical entity.
        // Expected: two full rows covering bytes 0x28–0x47.
        final long displayOffset = 0x10000000L;
        final int startIndex = 0x28;
        final int length32 = 32;
        out = new StringBuilder();
        HexDump.dump(testArray, displayOffset, out, startIndex, length32);
        assertEquals(
            "10000028 28 29 2A 2B 2C 2D 2E 2F 30 31 32 33 34 35 36 37 ()*+,-./01234567" + NL +
            "10000038 38 39 3A 3B 3C 3D 3E 3F 40 41 42 43 44 45 46 47 89:;<=>?@ABCDEFG" + NL,
            out.toString());

        // Dump 24 bytes starting at array index 0x40, with display offset 0.
        // 24 bytes = one full row (16 bytes) + one partial row (8 bytes); the partial
        // row is padded with spaces in the hex section to maintain column alignment.
        final int startIndex2 = 0x40;
        final int length24 = 24;
        out = new StringBuilder();
        HexDump.dump(testArray, 0, out, startIndex2, length24);
        assertEquals(
            "00000040 40 41 42 43 44 45 46 47 48 49 4A 4B 4C 4D 4E 4F @ABCDEFGHIJKLMNO" + NL +
            "00000050 50 51 52 53 54 55 56 57                         PQRSTUVW" + NL,
            out.toString());

        // A negative index is out of bounds
        assertThrows(ArrayIndexOutOfBoundsException.class,
            () -> HexDump.dump(testArray, displayOffset, new StringBuilder(), -1, testArray.length));

        // An index equal to the array length (one past the end) is out of bounds
        assertThrows(ArrayIndexOutOfBoundsException.class,
            () -> HexDump.dump(testArray, displayOffset, new StringBuilder(), testArray.length, testArray.length));

        // A negative length is out of bounds
        assertThrows(ArrayIndexOutOfBoundsException.class,
            () -> HexDump.dump(testArray, 0, new StringBuilder(), 0, -1));

        // A length that, when added to the index, exceeds the array size is out of bounds;
        // the exception message names the offending range explicitly
        final Exception exception = assertThrows(ArrayIndexOutOfBoundsException.class,
            () -> HexDump.dump(testArray, 0, new StringBuilder(), 1, testArray.length));
        assertEquals("Range [1, 1 + 256) out of bounds for length 256", exception.getMessage());

        // A null appendable should throw NullPointerException before any I/O occurs
        assertThrows(NullPointerException.class,
            () -> HexDump.dump(testArray, displayOffset, null, 0, testArray.length));
    }
}

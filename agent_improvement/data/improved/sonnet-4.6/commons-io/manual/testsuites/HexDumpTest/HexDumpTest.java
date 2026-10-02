/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.io.test.ThrowOnCloseOutputStream;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link HexDump}.
 */
class HexDumpTest {

    // Each output line: 8-char address + 1 space + 16*(2-char hex + 1 space) + 16 ASCII chars = 73
    private static final int LINE_LENGTH_WITHOUT_SEPARATOR = 73;

    @Test
    void testDumpAppendable() throws IOException {
        final byte[] testArray = sequentialByteArray(256);

        // Full array dump from address 0
        StringBuilder out = new StringBuilder();
        HexDump.dump(testArray, out);
        assertEquals(
            "00000000 00 01 02 03 04 05 06 07 08 09 0A 0B 0C 0D 0E 0F ................" + System.lineSeparator() +
            "00000010 10 11 12 13 14 15 16 17 18 19 1A 1B 1C 1D 1E 1F ................" + System.lineSeparator() +
            "00000020 20 21 22 23 24 25 26 27 28 29 2A 2B 2C 2D 2E 2F  !\"#$%&'()*+,-./" + System.lineSeparator() +
            "00000030 30 31 32 33 34 35 36 37 38 39 3A 3B 3C 3D 3E 3F 0123456789:;<=>?" + System.lineSeparator() +
            "00000040 40 41 42 43 44 45 46 47 48 49 4A 4B 4C 4D 4E 4F @ABCDEFGHIJKLMNO" + System.lineSeparator() +
            "00000050 50 51 52 53 54 55 56 57 58 59 5A 5B 5C 5D 5E 5F PQRSTUVWXYZ[\\]^_" + System.lineSeparator() +
            "00000060 60 61 62 63 64 65 66 67 68 69 6A 6B 6C 6D 6E 6F `abcdefghijklmno" + System.lineSeparator() +
            "00000070 70 71 72 73 74 75 76 77 78 79 7A 7B 7C 7D 7E 7F pqrstuvwxyz{|}~." + System.lineSeparator() +
            "00000080 80 81 82 83 84 85 86 87 88 89 8A 8B 8C 8D 8E 8F ................" + System.lineSeparator() +
            "00000090 90 91 92 93 94 95 96 97 98 99 9A 9B 9C 9D 9E 9F ................" + System.lineSeparator() +
            "000000A0 A0 A1 A2 A3 A4 A5 A6 A7 A8 A9 AA AB AC AD AE AF ................" + System.lineSeparator() +
            "000000B0 B0 B1 B2 B3 B4 B5 B6 B7 B8 B9 BA BB BC BD BE BF ................" + System.lineSeparator() +
            "000000C0 C0 C1 C2 C3 C4 C5 C6 C7 C8 C9 CA CB CC CD CE CF ................" + System.lineSeparator() +
            "000000D0 D0 D1 D2 D3 D4 D5 D6 D7 D8 D9 DA DB DC DD DE DF ................" + System.lineSeparator() +
            "000000E0 E0 E1 E2 E3 E4 E5 E6 E7 E8 E9 EA EB EC ED EE EF ................" + System.lineSeparator() +
            "000000F0 F0 F1 F2 F3 F4 F5 F6 F7 F8 F9 FA FB FC FD FE FF ................" + System.lineSeparator(),
            out.toString());

        // Partial dump: non-zero offset and non-zero start index with limited length
        out = new StringBuilder();
        HexDump.dump(testArray, 0x10000000, out, 0x28, 32);
        assertEquals(
            "10000028 28 29 2A 2B 2C 2D 2E 2F 30 31 32 33 34 35 36 37 ()*+,-./01234567" + System.lineSeparator() +
            "10000038 38 39 3A 3B 3C 3D 3E 3F 40 41 42 43 44 45 46 47 89:;<=>?@ABCDEFG" + System.lineSeparator(),
            out.toString());

        // Partial dump: zero offset with non-zero start index and length that produces a partial last row
        out = new StringBuilder();
        HexDump.dump(testArray, 0, out, 0x40, 24);
        assertEquals(
            "00000040 40 41 42 43 44 45 46 47 48 49 4A 4B 4C 4D 4E 4F @ABCDEFGHIJKLMNO" + System.lineSeparator() +
            "00000050 50 51 52 53 54 55 56 57                         PQRSTUVW" + System.lineSeparator(),
            out.toString());

        // Error cases: invalid index and length values
        assertThrows(ArrayIndexOutOfBoundsException.class,
            () -> HexDump.dump(testArray, 0x10000000, new StringBuilder(), -1, testArray.length));

        assertThrows(ArrayIndexOutOfBoundsException.class,
            () -> HexDump.dump(testArray, 0x10000000, new StringBuilder(), testArray.length, testArray.length));

        assertThrows(ArrayIndexOutOfBoundsException.class,
            () -> HexDump.dump(testArray, 0, new StringBuilder(), 0, -1));

        final Exception exception = assertThrows(ArrayIndexOutOfBoundsException.class,
            () -> HexDump.dump(testArray, 0, new StringBuilder(), 1, testArray.length));
        assertEquals("Range [1, 1 + 256) out of bounds for length 256", exception.getMessage());

        assertThrows(NullPointerException.class,
            () -> HexDump.dump(testArray, 0x10000000, null, 0, testArray.length));
    }

    @Test
    void testDumpOutputStream() throws IOException {
        final byte[] testArray = sequentialByteArray(256);

        // Full array dump with base address 0x00000000
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        HexDump.dump(testArray, 0, stream, 0);
        assertStreamEquals(buildExpectedFullDumpOutput("000000"), stream.toByteArray());

        // Full array dump with non-zero base address 0x10000000
        stream = new ByteArrayOutputStream();
        HexDump.dump(testArray, 0x10000000, stream, 0);
        assertStreamEquals(buildExpectedFullDumpOutput("100000"), stream.toByteArray());

        // Full array dump with large (negative-valued long) base address 0xFF000000
        stream = new ByteArrayOutputStream();
        HexDump.dump(testArray, 0xFF000000, stream, 0);
        assertStreamEquals(buildExpectedFullDumpOutput("FF0000"), stream.toByteArray());

        // Partial dump starting at array index 0x81, base address 0x10000000;
        // produces 8 rows where the last row is incomplete (0x81 + 7*16 = 0xF1, leaving 15 bytes)
        stream = new ByteArrayOutputStream();
        HexDump.dump(testArray, 0x10000000, stream, 0x81);
        assertStreamEquals(buildExpectedPartialDumpFromIndex0x81(), stream.toByteArray());

        // Error cases
        assertThrows(ArrayIndexOutOfBoundsException.class,
            () -> HexDump.dump(testArray, 0x10000000, new ByteArrayOutputStream(), -1));

        assertThrows(ArrayIndexOutOfBoundsException.class,
            () -> HexDump.dump(testArray, 0x10000000, new ByteArrayOutputStream(), testArray.length));

        assertThrows(NullPointerException.class,
            () -> HexDump.dump(testArray, 0x10000000, null, 0));

        // The dump method must not close the stream it writes to
        HexDump.dump(testArray, 0, new ThrowOnCloseOutputStream(new ByteArrayOutputStream()), 0);
    }

    /**
     * Builds expected hex-dump output bytes for all 16 rows of a 256-byte sequential array,
     * where the first 6 hex digits of each 8-char address are fixed by {@code addressPrefix6},
     * the 7th digit varies per row (0..F), and the 8th digit is always '0'.
     */
    private byte[] buildExpectedFullDumpOutput(final String addressPrefix6) {
        final int lineLength = LINE_LENGTH_WITHOUT_SEPARATOR + System.lineSeparator().length();
        final byte[] outputArray = new byte[16 * lineLength];
        for (int row = 0; row < 16; row++) {
            int pos = lineLength * row;
            for (int i = 0; i < 6; i++) {
                outputArray[pos++] = (byte) addressPrefix6.charAt(i);
            }
            outputArray[pos++] = (byte) toHex(row);
            outputArray[pos++] = (byte) '0';
            outputArray[pos++] = (byte) ' ';
            for (int col = 0; col < 16; col++) {
                outputArray[pos++] = (byte) toHex(row);
                outputArray[pos++] = (byte) toHex(col);
                outputArray[pos++] = (byte) ' ';
            }
            for (int col = 0; col < 16; col++) {
                outputArray[pos++] = (byte) toAscii(row * 16 + col);
            }
            System.arraycopy(System.lineSeparator().getBytes(), 0, outputArray, pos,
                    System.lineSeparator().getBytes().length);
        }
        return outputArray;
    }

    /**
     * Builds expected hex-dump output bytes for a partial dump of a 256-byte sequential
     * array starting at index 0x81 with base address 0x10000000.
     * Produces 8 rows; the last row is incomplete since index 0x81 + 7*16 = 0xF1, leaving
     * only 15 valid bytes (0xF1..0xFF). Blank hex slots and no ASCII char are emitted for
     * the 16th position on that row.
     */
    private byte[] buildExpectedPartialDumpFromIndex0x81() {
        final int lineLength = LINE_LENGTH_WITHOUT_SEPARATOR + System.lineSeparator().length();
        // The last row has 15 instead of 16 ASCII bytes, so total is 1 byte less than 8 full rows
        final byte[] outputArray = new byte[8 * lineLength - 1];
        for (int row = 0; row < 8; row++) {
            int pos = lineLength * row;
            outputArray[pos++] = (byte) '1';
            outputArray[pos++] = (byte) '0';
            outputArray[pos++] = (byte) '0';
            outputArray[pos++] = (byte) '0';
            outputArray[pos++] = (byte) '0';
            outputArray[pos++] = (byte) '0';
            outputArray[pos++] = (byte) toHex(row + 8);
            outputArray[pos++] = (byte) '1';
            outputArray[pos++] = (byte) ' ';
            for (int col = 0; col < 16; col++) {
                final int byteIndex = 0x81 + row * 16 + col;
                if (byteIndex < 0x100) {
                    outputArray[pos++] = (byte) toHex(byteIndex / 16);
                    outputArray[pos++] = (byte) toHex(byteIndex);
                } else {
                    outputArray[pos++] = (byte) ' ';
                    outputArray[pos++] = (byte) ' ';
                }
                outputArray[pos++] = (byte) ' ';
            }
            for (int col = 0; col < 16; col++) {
                final int byteIndex = 0x81 + row * 16 + col;
                if (byteIndex < 0x100) {
                    outputArray[pos++] = (byte) toAscii(byteIndex);
                }
            }
            System.arraycopy(System.lineSeparator().getBytes(), 0, outputArray, pos,
                    System.lineSeparator().getBytes().length);
        }
        return outputArray;
    }

    /** Asserts that two byte arrays have the same length and identical element values. */
    private void assertStreamEquals(final byte[] expected, final byte[] actual) {
        assertEquals(expected.length, actual.length, "array size mismatch");
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual[i], "array[ " + i + "] mismatch");
        }
    }

    /** Returns a byte array of the given length where element {@code i} equals {@code (byte) i}. */
    private static byte[] sequentialByteArray(final int length) {
        final byte[] array = new byte[length];
        for (int i = 0; i < length; i++) {
            array[i] = (byte) i;
        }
        return array;
    }

    /** Converts a byte value to its printable ASCII character; non-printable values (outside 32..126) become '.'. */
    private char toAscii(final int byteValue) {
        if (byteValue >= 32 && byteValue <= 126) {
            return (char) byteValue;
        }
        return '.';
    }

    /** Returns the uppercase hex digit for the lower 4 bits of {@code nibble}. */
    private char toHex(final int nibble) {
        final char[] hexChars = {
            '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };
        return hexChars[nibble % 16];
    }
}

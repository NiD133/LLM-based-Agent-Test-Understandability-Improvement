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

    /** Number of bytes in the test data array: one for every possible byte value. */
    private static final int DATA_SIZE = 256;

    /** Number of data bytes rendered on each line of a hex dump. */
    private static final int BYTES_PER_LINE = 16;

    /**
     * Number of characters in a full hex-dump line, excluding the trailing line separator:
     * 8 (hex offset) + 1 (space) + 16 * 3 (two hex digits and a space per byte) + 16 (ASCII column) = 73.
     */
    private static final int LINE_TEXT_LENGTH = 8 + 1 + BYTES_PER_LINE * 3 + BYTES_PER_LINE;

    /** Full width of a dumped line in bytes, including the platform line separator. */
    private static final int LINE_WIDTH = LINE_TEXT_LENGTH + System.lineSeparator().length();

    /**
     * Builds a byte array of the given size whose value at index {@code i} is {@code (byte) i},
     * i.e. the bytes 0x00..0xFF in order.
     */
    private static byte[] createSequentialData(final int size) {
        final byte[] data = new byte[size];
        for (int i = 0; i < size; i++) {
            data[i] = (byte) i;
        }
        return data;
    }

    @Test
    void testDumpAppendable() throws IOException {
        final byte[] testArray = createSequentialData(DATA_SIZE);

        // Dump the entire array.
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

        // Non-zero offset, non-zero index, length shorter than the array.
        out = new StringBuilder();
        HexDump.dump(testArray, 0x10000000, out, 0x28, 32);
        assertEquals(
            "10000028 28 29 2A 2B 2C 2D 2E 2F 30 31 32 33 34 35 36 37 ()*+,-./01234567" + System.lineSeparator() +
            "10000038 38 39 3A 3B 3C 3D 3E 3F 40 41 42 43 44 45 46 47 89:;<=>?@ABCDEFG" + System.lineSeparator(),
            out.toString());

        // Non-zero index with a length that leaves a partially filled final line.
        out = new StringBuilder();
        HexDump.dump(testArray, 0, out, 0x40, 24);
        assertEquals(
            "00000040 40 41 42 43 44 45 46 47 48 49 4A 4B 4C 4D 4E 4F @ABCDEFGHIJKLMNO" + System.lineSeparator() +
            "00000050 50 51 52 53 54 55 56 57                         PQRSTUVW" + System.lineSeparator(),
            out.toString());

        // Negative index is rejected.
        assertThrows(ArrayIndexOutOfBoundsException.class,
            () -> HexDump.dump(testArray, 0x10000000, new StringBuilder(), -1, testArray.length));

        // Index past the end of the array is rejected.
        assertThrows(ArrayIndexOutOfBoundsException.class,
            () -> HexDump.dump(testArray, 0x10000000, new StringBuilder(), testArray.length, testArray.length));

        // Negative length is rejected.
        assertThrows(ArrayIndexOutOfBoundsException.class,
            () -> HexDump.dump(testArray, 0, new StringBuilder(), 0, -1));

        // Length that runs past the end of the array is rejected, with a descriptive message.
        final Exception exception = assertThrows(ArrayIndexOutOfBoundsException.class,
            () -> HexDump.dump(testArray, 0, new StringBuilder(), 1, testArray.length));
        assertEquals("Range [1, 1 + 256) out of bounds for length 256", exception.getMessage());

        // Null appendable is rejected.
        assertThrows(NullPointerException.class,
            () -> HexDump.dump(testArray, 0x10000000, null, 0, testArray.length));
    }

    @Test
    void testDumpOutputStream() throws IOException {
        final byte[] testArray = createSequentialData(DATA_SIZE);

        // Dump the whole array from offset 0: every line offset begins with "000000".
        assertDumpEquals(expectedFullDump("000000"), dumpToBytes(testArray, 0, 0));

        // Non-zero offset: every line offset begins with "100000".
        assertDumpEquals(expectedFullDump("100000"), dumpToBytes(testArray, 0x10000000, 0));

        // "Negative" offset (high bit set): only the low 32 bits are printed, so lines begin with "FF0000".
        assertDumpEquals(expectedFullDump("FF0000"), dumpToBytes(testArray, 0xFF000000, 0));

        // Non-zero index: dump only the tail of the array starting at index 0x81.
        assertDumpEquals(expectedDumpFromIndex0x81(), dumpToBytes(testArray, 0x10000000, 0x81));

        // Negative index is rejected.
        assertThrows(ArrayIndexOutOfBoundsException.class,
            () -> HexDump.dump(testArray, 0x10000000, new ByteArrayOutputStream(), -1));

        // Index past the end of the array is rejected.
        assertThrows(ArrayIndexOutOfBoundsException.class,
            () -> HexDump.dump(testArray, 0x10000000, new ByteArrayOutputStream(), testArray.length));

        // Null stream is rejected.
        assertThrows(NullPointerException.class,
            () -> HexDump.dump(testArray, 0x10000000, null, 0));

        // The dump method must not close the caller's stream.
        HexDump.dump(testArray, 0, new ThrowOnCloseOutputStream(new ByteArrayOutputStream()), 0);
    }

    /**
     * Dumps {@code data} to a fresh {@link ByteArrayOutputStream} and returns the bytes written.
     */
    private byte[] dumpToBytes(final byte[] data, final long offset, final int index) throws IOException {
        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        HexDump.dump(data, offset, stream, index);
        return stream.toByteArray();
    }

    /**
     * Builds the expected dump of the full 256-byte sequential array: 16 lines of 16 bytes each.
     *
     * @param offsetPrefix the leading 6 characters of every line's 8-character hex offset; the
     *                     remaining two characters are the line number (0..F) followed by '0'.
     */
    private byte[] expectedFullDump(final String offsetPrefix) {
        final byte[] expected = new byte[BYTES_PER_LINE * LINE_WIDTH];
        for (int line = 0; line < BYTES_PER_LINE; line++) {
            int pos = LINE_WIDTH * line;

            pos = writeChars(expected, pos, offsetPrefix);
            expected[pos++] = (byte) toHex(line);
            expected[pos++] = (byte) '0';
            expected[pos++] = (byte) ' ';
            for (int col = 0; col < BYTES_PER_LINE; col++) {
                expected[pos++] = (byte) toHex(line);
                expected[pos++] = (byte) toHex(col);
                expected[pos++] = (byte) ' ';
            }
            for (int col = 0; col < BYTES_PER_LINE; col++) {
                expected[pos++] = (byte) toAscii(line * BYTES_PER_LINE + col);
            }
            writeLineSeparator(expected, pos);
        }
        return expected;
    }

    /**
     * Builds the expected dump of the array tail starting at index 0x81 with a printed offset base
     * of 0x10000000. This covers 127 bytes (0x81..0xFF) over 8 lines; the final line is partially
     * filled, so positions at or beyond index 0x100 are rendered as spaces and contribute no ASCII.
     */
    private byte[] expectedDumpFromIndex0x81() {
        final int lines = 8;
        final byte[] expected = new byte[lines * LINE_WIDTH - 1];
        for (int line = 0; line < lines; line++) {
            int pos = LINE_WIDTH * line;

            pos = writeChars(expected, pos, "100000");
            expected[pos++] = (byte) toHex(line + 8);
            expected[pos++] = (byte) '1';
            expected[pos++] = (byte) ' ';
            for (int col = 0; col < BYTES_PER_LINE; col++) {
                final int index = 0x81 + line * BYTES_PER_LINE + col;
                if (index < 0x100) {
                    expected[pos++] = (byte) toHex(index / 16);
                    expected[pos++] = (byte) toHex(index);
                } else {
                    expected[pos++] = (byte) ' ';
                    expected[pos++] = (byte) ' ';
                }
                expected[pos++] = (byte) ' ';
            }
            for (int col = 0; col < BYTES_PER_LINE; col++) {
                final int index = 0x81 + line * BYTES_PER_LINE + col;
                if (index < 0x100) {
                    expected[pos++] = (byte) toAscii(index);
                }
            }
            writeLineSeparator(expected, pos);
        }
        return expected;
    }

    /**
     * Asserts that {@code actual} matches {@code expected} byte for byte, reporting the first
     * differing position to ease debugging.
     */
    private void assertDumpEquals(final byte[] expected, final byte[] actual) {
        assertEquals(expected.length, actual.length, "array size mismatch");
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual[i], "array[ " + i + "] mismatch");
        }
    }

    /** Writes the characters of {@code text} into {@code target} starting at {@code pos}; returns the next position. */
    private static int writeChars(final byte[] target, int pos, final String text) {
        for (int i = 0; i < text.length(); i++) {
            target[pos++] = (byte) text.charAt(i);
        }
        return pos;
    }

    /** Copies the platform line separator into {@code target} starting at {@code pos}. */
    private static void writeLineSeparator(final byte[] target, final int pos) {
        final byte[] separator = System.lineSeparator().getBytes();
        System.arraycopy(separator, 0, target, pos, separator.length);
    }

    private char toAscii(final int c) {
        char rval = '.';

        if (c >= 32 && c <= 126) {
            rval = (char) c;
        }
        return rval;
    }

    private char toHex(final int n) {
        final char[] hexChars =
                {
                    '0', '1', '2', '3', '4', '5', '6', '7',
                    '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
                };

        return hexChars[n % 16];
    }
}

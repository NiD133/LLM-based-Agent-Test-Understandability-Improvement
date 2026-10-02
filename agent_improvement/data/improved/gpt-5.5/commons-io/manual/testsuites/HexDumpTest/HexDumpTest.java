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

    private static final int BYTE_VALUES = 256;
    private static final int BYTES_PER_ROW = 16;
    private static final int DUMP_ROW_WIDTH = 73;
    private static final String EOL = System.lineSeparator();

    @Test
    void testDumpAppendable() throws IOException {
        final byte[] testArray = createSequentialByteArray();

        StringBuilder out = new StringBuilder();
        HexDump.dump(testArray, out);
        assertEquals(
            "00000000 00 01 02 03 04 05 06 07 08 09 0A 0B 0C 0D 0E 0F ................" + EOL +
            "00000010 10 11 12 13 14 15 16 17 18 19 1A 1B 1C 1D 1E 1F ................" + EOL +
            "00000020 20 21 22 23 24 25 26 27 28 29 2A 2B 2C 2D 2E 2F  !\"#$%&'()*+,-./" + EOL +
            "00000030 30 31 32 33 34 35 36 37 38 39 3A 3B 3C 3D 3E 3F 0123456789:;<=>?" + EOL +
            "00000040 40 41 42 43 44 45 46 47 48 49 4A 4B 4C 4D 4E 4F @ABCDEFGHIJKLMNO" + EOL +
            "00000050 50 51 52 53 54 55 56 57 58 59 5A 5B 5C 5D 5E 5F PQRSTUVWXYZ[\\]^_" + EOL +
            "00000060 60 61 62 63 64 65 66 67 68 69 6A 6B 6C 6D 6E 6F `abcdefghijklmno" + EOL +
            "00000070 70 71 72 73 74 75 76 77 78 79 7A 7B 7C 7D 7E 7F pqrstuvwxyz{|}~." + EOL +
            "00000080 80 81 82 83 84 85 86 87 88 89 8A 8B 8C 8D 8E 8F ................" + EOL +
            "00000090 90 91 92 93 94 95 96 97 98 99 9A 9B 9C 9D 9E 9F ................" + EOL +
            "000000A0 A0 A1 A2 A3 A4 A5 A6 A7 A8 A9 AA AB AC AD AE AF ................" + EOL +
            "000000B0 B0 B1 B2 B3 B4 B5 B6 B7 B8 B9 BA BB BC BD BE BF ................" + EOL +
            "000000C0 C0 C1 C2 C3 C4 C5 C6 C7 C8 C9 CA CB CC CD CE CF ................" + EOL +
            "000000D0 D0 D1 D2 D3 D4 D5 D6 D7 D8 D9 DA DB DC DD DE DF ................" + EOL +
            "000000E0 E0 E1 E2 E3 E4 E5 E6 E7 E8 E9 EA EB EC ED EE EF ................" + EOL +
            "000000F0 F0 F1 F2 F3 F4 F5 F6 F7 F8 F9 FA FB FC FD FE FF ................" + EOL,
            out.toString());

        out = new StringBuilder();
        HexDump.dump(testArray, 0x10000000, out, 0x28, 32);
        assertEquals(
            "10000028 28 29 2A 2B 2C 2D 2E 2F 30 31 32 33 34 35 36 37 ()*+,-./01234567" + EOL +
            "10000038 38 39 3A 3B 3C 3D 3E 3F 40 41 42 43 44 45 46 47 89:;<=>?@ABCDEFG" + EOL,
            out.toString());

        out = new StringBuilder();
        HexDump.dump(testArray, 0, out, 0x40, 24);
        assertEquals(
            "00000040 40 41 42 43 44 45 46 47 48 49 4A 4B 4C 4D 4E 4F @ABCDEFGHIJKLMNO" + EOL +
            "00000050 50 51 52 53 54 55 56 57                         PQRSTUVW" + EOL,
            out.toString());

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> HexDump.dump(testArray, 0x10000000, new StringBuilder(), -1, testArray.length));
        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> HexDump.dump(testArray, 0x10000000, new StringBuilder(), testArray.length, testArray.length));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> HexDump.dump(testArray, 0, new StringBuilder(), 0, -1));

        final Exception exception = assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> HexDump.dump(testArray, 0, new StringBuilder(), 1, testArray.length));
        assertEquals("Range [1, 1 + 256) out of bounds for length 256", exception.getMessage());

        assertThrows(NullPointerException.class, () -> HexDump.dump(testArray, 0x10000000, null, 0, testArray.length));
    }

    @Test
    void testDumpOutputStream() throws IOException {
        final byte[] testArray = createSequentialByteArray();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        HexDump.dump(testArray, 0, stream, 0);
        assertEqualsExpectedBytes(createExpectedFullDump('0', '0'), stream.toByteArray());

        stream = new ByteArrayOutputStream();
        HexDump.dump(testArray, 0x10000000, stream, 0);
        assertEqualsExpectedBytes(createExpectedFullDump('1', '0'), stream.toByteArray());

        stream = new ByteArrayOutputStream();
        HexDump.dump(testArray, 0xFF000000, stream, 0);
        assertEqualsExpectedBytes(createExpectedFullDump('F', 'F'), stream.toByteArray());

        stream = new ByteArrayOutputStream();
        HexDump.dump(testArray, 0x10000000, stream, 0x81);
        assertEqualsExpectedBytes(createExpectedDumpFromIndex0x81(), stream.toByteArray());

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> HexDump.dump(testArray, 0x10000000, new ByteArrayOutputStream(), -1));
        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> HexDump.dump(testArray, 0x10000000, new ByteArrayOutputStream(), testArray.length));
        assertThrows(NullPointerException.class, () -> HexDump.dump(testArray, 0x10000000, null, 0));

        HexDump.dump(testArray, 0, new ThrowOnCloseOutputStream(new ByteArrayOutputStream()), 0);
    }

    private static void appendLineSeparator(final byte[] outputArray, final int offset) {
        final byte[] lineSeparator = EOL.getBytes();
        System.arraycopy(lineSeparator, 0, outputArray, offset, lineSeparator.length);
    }

    private static byte[] createExpectedDumpFromIndex0x81() {
        final byte[] outputArray = new byte[8 * bytesPerRowWithSeparator() - 1];

        for (int row = 0; row < 8; row++) {
            int offset = bytesPerRowWithSeparator() * row;

            outputArray[offset++] = (byte) '1';
            outputArray[offset++] = (byte) '0';
            outputArray[offset++] = (byte) '0';
            outputArray[offset++] = (byte) '0';
            outputArray[offset++] = (byte) '0';
            outputArray[offset++] = (byte) '0';
            outputArray[offset++] = (byte) toHex(row + 8);
            outputArray[offset++] = (byte) '1';
            outputArray[offset++] = (byte) ' ';
            for (int column = 0; column < BYTES_PER_ROW; column++) {
                final int index = 0x81 + row * BYTES_PER_ROW + column;

                if (index < BYTE_VALUES) {
                    outputArray[offset++] = (byte) toHex(index / BYTES_PER_ROW);
                    outputArray[offset++] = (byte) toHex(index);
                } else {
                    outputArray[offset++] = (byte) ' ';
                    outputArray[offset++] = (byte) ' ';
                }
                outputArray[offset++] = (byte) ' ';
            }
            for (int column = 0; column < BYTES_PER_ROW; column++) {
                final int index = 0x81 + row * BYTES_PER_ROW + column;

                if (index < BYTE_VALUES) {
                    outputArray[offset++] = (byte) toAscii(index);
                }
            }
            appendLineSeparator(outputArray, offset);
        }
        return outputArray;
    }

    private static byte[] createExpectedFullDump(final char firstOffsetDigit, final char secondOffsetDigit) {
        final byte[] outputArray = new byte[16 * bytesPerRowWithSeparator()];

        for (int row = 0; row < BYTES_PER_ROW; row++) {
            int offset = bytesPerRowWithSeparator() * row;

            outputArray[offset++] = (byte) firstOffsetDigit;
            outputArray[offset++] = (byte) secondOffsetDigit;
            outputArray[offset++] = (byte) '0';
            outputArray[offset++] = (byte) '0';
            outputArray[offset++] = (byte) '0';
            outputArray[offset++] = (byte) '0';
            outputArray[offset++] = (byte) toHex(row);
            outputArray[offset++] = (byte) '0';
            outputArray[offset++] = (byte) ' ';
            for (int column = 0; column < BYTES_PER_ROW; column++) {
                outputArray[offset++] = (byte) toHex(row);
                outputArray[offset++] = (byte) toHex(column);
                outputArray[offset++] = (byte) ' ';
            }
            for (int column = 0; column < BYTES_PER_ROW; column++) {
                outputArray[offset++] = (byte) toAscii(row * BYTES_PER_ROW + column);
            }
            appendLineSeparator(outputArray, offset);
        }
        return outputArray;
    }

    private static byte[] createSequentialByteArray() {
        final byte[] testArray = new byte[BYTE_VALUES];

        for (int index = 0; index < BYTE_VALUES; index++) {
            testArray[index] = (byte) index;
        }
        return testArray;
    }

    private static int bytesPerRowWithSeparator() {
        return DUMP_ROW_WIDTH + EOL.length();
    }

    private static void assertEqualsExpectedBytes(final byte[] expectedOutput, final byte[] actualOutput) {
        assertEquals(expectedOutput.length, actualOutput.length, "array size mismatch");
        for (int index = 0; index < expectedOutput.length; index++) {
            assertEquals(expectedOutput[index], actualOutput[index], "array[ " + index + "] mismatch");
        }
    }

    private static char toAscii(final int c) {
        char rval = '.';

        if (c >= 32 && c <= 126) {
            rval = (char) c;
        }
        return rval;
    }

    private static char toHex(final int n) {
        final char[] hexChars =
                {
                    '0', '1', '2', '3', '4', '5', '6', '7',
                    '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
                };

        return hexChars[n % BYTES_PER_ROW];
    }
}

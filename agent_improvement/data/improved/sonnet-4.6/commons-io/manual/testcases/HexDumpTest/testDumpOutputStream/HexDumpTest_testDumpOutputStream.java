package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.io.test.ThrowOnCloseOutputStream;
import org.junit.jupiter.api.Test;

public class HexDumpTest_testDumpOutputStream {

    // Hex dump line format: 8-char address + ' ' + 16*(2-char hex + ' ') + 16 ASCII chars + line separator
    private static final int BYTES_PER_ROW = 16;
    private static final int ADDRESS_CHARS = 8;
    private static final int CHARS_PER_LINE = ADDRESS_CHARS + 1 + BYTES_PER_ROW * 3 + BYTES_PER_ROW; // = 73
    private static final int TEST_ARRAY_SIZE = 256;

    private char toAscii(final int c) {
        return (c >= 32 && c <= 126) ? (char) c : '.';
    }

    private char toHex(final int n) {
        final char[] hexChars = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        return hexChars[n % 16];
    }

    private void assertBytesEqual(byte[] expected, byte[] actual) {
        assertEquals(expected.length, actual.length, "array size mismatch");
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual[i], "array[ " + i + "] mismatch");
        }
    }

    /**
     * Builds the expected output for a full 256-byte dump (16 complete rows of 16 bytes each).
     *
     * @param addressPrefix the first 6 characters of the 8-character hex address field;
     *                      the remaining 2 chars are the row-index nibble followed by '0'
     *                      (since each row spans 16 = 0x10 bytes)
     */
    private byte[] buildExpectedFullDump(final String addressPrefix) {
        final int lineLen = CHARS_PER_LINE + System.lineSeparator().length();
        final int rows = TEST_ARRAY_SIZE / BYTES_PER_ROW; // 16 rows
        final byte[] expected = new byte[rows * lineLen];

        for (int row = 0; row < rows; row++) {
            int pos = lineLen * row;

            // 8-char hex address: provided prefix + row nibble + '0'
            for (final char c : addressPrefix.toCharArray()) {
                expected[pos++] = (byte) c;
            }
            expected[pos++] = (byte) toHex(row);
            expected[pos++] = (byte) '0';
            expected[pos++] = (byte) ' ';

            // 16 hex byte values (high nibble = row, low nibble = column), each followed by a space
            for (int col = 0; col < BYTES_PER_ROW; col++) {
                expected[pos++] = (byte) toHex(row);
                expected[pos++] = (byte) toHex(col);
                expected[pos++] = (byte) ' ';
            }

            // 16 ASCII characters — printable bytes as-is, non-printable as '.'
            for (int col = 0; col < BYTES_PER_ROW; col++) {
                expected[pos++] = (byte) toAscii(row * BYTES_PER_ROW + col);
            }

            System.arraycopy(System.lineSeparator().getBytes(), 0, expected, pos,
                    System.lineSeparator().length());
        }
        return expected;
    }

    /**
     * Builds the expected output for a partial dump starting at array index 0x81 with base
     * offset 0x10000000. Covers 127 bytes (indices 0x81–0xFF) across 8 rows; the final row
     * has only 15 bytes, making the last line 1 byte shorter than a full line.
     */
    private byte[] buildExpectedPartialDump() {
        final int startIndex = 0x81;
        final int endIndex = TEST_ARRAY_SIZE; // exclusive
        final int rows = 8;
        final int lineLen = CHARS_PER_LINE + System.lineSeparator().length();
        // Last row has 15 ASCII chars instead of 16, so overall output is 1 byte shorter
        final byte[] expected = new byte[rows * lineLen - 1];

        for (int row = 0; row < rows; row++) {
            int pos = lineLen * row;

            // Address: 0x10000000 + 0x81 + row*16 = 0x10000081 + row*0x10
            // -> "100000" + toHex(8 + row) + "1"
            expected[pos++] = (byte) '1';
            expected[pos++] = (byte) '0';
            expected[pos++] = (byte) '0';
            expected[pos++] = (byte) '0';
            expected[pos++] = (byte) '0';
            expected[pos++] = (byte) '0';
            expected[pos++] = (byte) toHex(row + 8);
            expected[pos++] = (byte) '1';
            expected[pos++] = (byte) ' ';

            // 16 hex value slots: present bytes get their hex digits, absent bytes get spaces
            for (int col = 0; col < BYTES_PER_ROW; col++) {
                final int byteIndex = startIndex + row * BYTES_PER_ROW + col;
                if (byteIndex < endIndex) {
                    expected[pos++] = (byte) toHex(byteIndex / BYTES_PER_ROW);
                    expected[pos++] = (byte) toHex(byteIndex);
                } else {
                    expected[pos++] = (byte) ' ';
                    expected[pos++] = (byte) ' ';
                }
                expected[pos++] = (byte) ' ';
            }

            // ASCII chars for present bytes only — no padding for absent trailing bytes
            for (int col = 0; col < BYTES_PER_ROW; col++) {
                final int byteIndex = startIndex + row * BYTES_PER_ROW + col;
                if (byteIndex < endIndex) {
                    expected[pos++] = (byte) toAscii(byteIndex);
                }
            }

            System.arraycopy(System.lineSeparator().getBytes(), 0, expected, pos,
                    System.lineSeparator().length());
        }
        return expected;
    }

    @Test
    void testDumpOutputStream() throws IOException {
        final byte[] testArray = new byte[TEST_ARRAY_SIZE];
        for (int i = 0; i < TEST_ARRAY_SIZE; i++) {
            testArray[i] = (byte) i;
        }

        // Dump entire array with zero base offset — addresses start at 0x00000000
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        HexDump.dump(testArray, 0, stream, 0);
        assertBytesEqual(buildExpectedFullDump("000000"), stream.toByteArray());

        // Dump entire array with positive base offset — addresses start at 0x10000000
        stream = new ByteArrayOutputStream();
        HexDump.dump(testArray, 0x10000000, stream, 0);
        assertBytesEqual(buildExpectedFullDump("100000"), stream.toByteArray());

        // Dump entire array with large (wrapping) base offset — addresses start at 0xFF000000
        stream = new ByteArrayOutputStream();
        HexDump.dump(testArray, 0xFF000000, stream, 0);
        assertBytesEqual(buildExpectedFullDump("FF0000"), stream.toByteArray());

        // Dump from index 0x81 with base offset 0x10000000 — produces a partial last row
        stream = new ByteArrayOutputStream();
        HexDump.dump(testArray, 0x10000000, stream, 0x81);
        assertBytesEqual(buildExpectedPartialDump(), stream.toByteArray());

        // Negative index must throw
        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> HexDump.dump(testArray, 0x10000000, new ByteArrayOutputStream(), -1));

        // Index beyond array bounds must throw
        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> HexDump.dump(testArray, 0x10000000, new ByteArrayOutputStream(), testArray.length));

        // Null output stream must throw
        assertThrows(NullPointerException.class,
                () -> HexDump.dump(testArray, 0x10000000, null, 0));

        // dump() must not close the output stream
        HexDump.dump(testArray, 0, new ThrowOnCloseOutputStream(new ByteArrayOutputStream()), 0);
    }
}

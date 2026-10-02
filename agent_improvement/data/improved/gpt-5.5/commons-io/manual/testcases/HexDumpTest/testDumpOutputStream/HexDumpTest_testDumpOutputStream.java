package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.io.test.ThrowOnCloseOutputStream;
import org.junit.jupiter.api.Test;

public class HexDumpTest_testDumpOutputStream {

    private static final int BYTES_PER_LINE = 16;
    private static final int FULL_LINE_LENGTH = 73;

    private char toAscii(final int c) {
        char rval = '.';
        if (c >= 32 && c <= 126) {
            rval = (char) c;
        }
        return rval;
    }

    private char toHex(final int n) {
        final char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        return hexChars[n % 16];
    }

    @Test
    void testDumpOutputStream() throws IOException {
        final byte[] testArray = createSequentialByteArray();

        assertDumpMatchesExpected(testArray, 0, 0, "00000");
        assertDumpMatchesExpected(testArray, 0x10000000, 0, "10000");
        assertDumpMatchesExpected(testArray, 0xFF000000, 0, "FF000");
        assertDumpMatchesExpected(testArray, 0x10000000, 0x81, "10000");

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> HexDump.dump(testArray, 0x10000000, new ByteArrayOutputStream(), -1));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> HexDump.dump(testArray, 0x10000000, new ByteArrayOutputStream(), testArray.length));
        assertThrows(NullPointerException.class, () -> HexDump.dump(testArray, 0x10000000, null, 0));

        HexDump.dump(testArray, 0, new ThrowOnCloseOutputStream(new ByteArrayOutputStream()), 0);
    }

    private byte[] createSequentialByteArray() {
        final byte[] testArray = new byte[256];
        for (int j = 0; j < 256; j++) {
            testArray[j] = (byte) j;
        }
        return testArray;
    }

    private void assertDumpMatchesExpected(final byte[] testArray, final long offset, final int index, final String offsetPrefix)
            throws IOException {
        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        HexDump.dump(testArray, offset, stream, index);

        final byte[] expectedOutput = buildExpectedOutput(index, offsetPrefix);
        final byte[] actualOutput = stream.toByteArray();
        assertEquals(expectedOutput.length, actualOutput.length, "array size mismatch");
        for (int j = 0; j < expectedOutput.length; j++) {
            assertEquals(expectedOutput[j], actualOutput[j], "array[ " + j + "] mismatch");
        }
    }

    private byte[] buildExpectedOutput(final int index, final String offsetPrefix) {
        final byte[] lineSeparator = System.lineSeparator().getBytes();
        final int lineLength = FULL_LINE_LENGTH + lineSeparator.length;
        final int lineCount = (256 - index + BYTES_PER_LINE - 1) / BYTES_PER_LINE;
        final int lastLineByteCount = (256 - index) % BYTES_PER_LINE == 0 ? BYTES_PER_LINE : (256 - index) % BYTES_PER_LINE;
        final int outputLength = lineCount * lineLength - (BYTES_PER_LINE - lastLineByteCount);
        final byte[] outputArray = new byte[outputLength];

        for (int line = 0; line < lineCount; line++) {
            writeExpectedLine(outputArray, line * lineLength, lineSeparator, offsetPrefix, index, line);
        }
        return outputArray;
    }

    private void writeExpectedLine(final byte[] outputArray, int offset, final byte[] lineSeparator, final String offsetPrefix,
            final int index, final int line) {
        final int lineStartIndex = index + line * BYTES_PER_LINE;

        for (int j = 0; j < offsetPrefix.length(); j++) {
            outputArray[offset++] = (byte) offsetPrefix.charAt(j);
        }
        outputArray[offset++] = (byte) toHex(lineStartIndex / BYTES_PER_LINE);
        outputArray[offset++] = (byte) toHex(lineStartIndex);
        outputArray[offset++] = (byte) ' ';

        for (int k = 0; k < BYTES_PER_LINE; k++) {
            final int value = lineStartIndex + k;
            if (value < 0x100) {
                outputArray[offset++] = (byte) toHex(value / BYTES_PER_LINE);
                outputArray[offset++] = (byte) toHex(value);
            } else {
                outputArray[offset++] = (byte) ' ';
                outputArray[offset++] = (byte) ' ';
            }
            outputArray[offset++] = (byte) ' ';
        }

        for (int k = 0; k < BYTES_PER_LINE; k++) {
            final int value = lineStartIndex + k;
            if (value < 0x100) {
                outputArray[offset++] = (byte) toAscii(value);
            }
        }
        System.arraycopy(lineSeparator, 0, outputArray, offset, lineSeparator.length);
    }
}

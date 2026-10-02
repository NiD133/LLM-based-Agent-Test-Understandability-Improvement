package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.io.test.ThrowOnCloseOutputStream;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link HexDump#dump(byte[], long, java.io.OutputStream, int)}.
 *
 * <p>The dump renders one line per 16 bytes. Every line has the shape:</p>
 * <pre>
 *   00000000 00 01 02 ... 0F ................
 *   |------| |--- 16 hex columns ---| |- ASCII -|
 *   offset                            printable bytes
 * </pre>
 * <p>The helper {@link #expectedDump(byte[], long, int)} reproduces that exact
 * format so each test scenario can simply compare the helper's output against
 * what {@code HexDump.dump} actually wrote.</p>
 */
public class HexDumpTest_testDumpOutputStream {

    /** Bytes per dump line. */
    private static final int BYTES_PER_LINE = 16;

    /** Platform line separator, written at the end of every dump line. */
    private static final String EOL = System.lineSeparator();

    /** A 256-byte array whose value at every position equals its index (0..255). */
    private static byte[] newSequentialBytes() {
        final byte[] data = new byte[256];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) i;
        }
        return data;
    }

    /**
     * Builds the text that {@code HexDump.dump} is expected to produce for the
     * bytes of {@code data} starting at {@code index}, when the array's first
     * dumped byte sits at the given {@code offset} within a larger entity.
     */
    private static String expectedDump(final byte[] data, final long offset, final int index) {
        final StringBuilder expected = new StringBuilder();
        long lineOffset = offset + index;
        for (int lineStart = index; lineStart < data.length; lineStart += BYTES_PER_LINE) {
            final int bytesOnLine = Math.min(BYTES_PER_LINE, data.length - lineStart);

            // 8-digit hex offset of the first byte on this line.
            expected.append(String.format("%08X", lineOffset & 0xFFFFFFFFL)).append(' ');

            // 16 two-digit hex columns; missing trailing columns are blanked.
            for (int col = 0; col < BYTES_PER_LINE; col++) {
                if (col < bytesOnLine) {
                    expected.append(String.format("%02X", data[lineStart + col] & 0xFF));
                } else {
                    expected.append("  ");
                }
                expected.append(' ');
            }

            // Printable-ASCII rendering of the same bytes ('.' for non-printable).
            for (int col = 0; col < bytesOnLine; col++) {
                expected.append(toPrintableAscii(data[lineStart + col]));
            }

            expected.append(EOL);
            lineOffset += bytesOnLine;
        }
        return expected.toString();
    }

    /** Returns the byte as a character if it is printable ASCII, otherwise '.'. */
    private static char toPrintableAscii(final byte b) {
        final int value = b & 0xFF;
        return value >= 32 && value <= 126 ? (char) value : '.';
    }

    /** Dumps {@code data} and asserts the produced bytes match {@link #expectedDump}. */
    private static void assertDump(final byte[] data, final long offset, final int index) throws IOException {
        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        HexDump.dump(data, offset, stream, index);
        final String actual = new String(stream.toByteArray(), Charset.defaultCharset());
        assertEquals(expectedDump(data, offset, index), actual);
    }

    @Test
    void testDumpOutputStream() throws IOException {
        final byte[] testArray = newSequentialBytes();

        // Whole array, zero offset and index.
        assertDump(testArray, 0, 0);

        // Non-zero offset: the printed line offsets are shifted accordingly.
        assertDump(testArray, 0x10000000, 0);

        // "Negative" offset (high bit set): only the low 32 bits are printed.
        assertDump(testArray, 0xFF000000, 0);

        // Non-zero start index: dumping begins partway through the array.
        assertDump(testArray, 0x10000000, 0x81);

        // Negative index is rejected.
        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> HexDump.dump(testArray, 0x10000000, new ByteArrayOutputStream(), -1));

        // Index at or beyond the array length is rejected.
        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> HexDump.dump(testArray, 0x10000000, new ByteArrayOutputStream(), testArray.length));

        // A null stream is rejected.
        assertThrows(NullPointerException.class,
                () -> HexDump.dump(testArray, 0x10000000, null, 0));

        // The dump method must not close the caller's stream.
        HexDump.dump(testArray, 0, new ThrowOnCloseOutputStream(new ByteArrayOutputStream()), 0);
    }
}

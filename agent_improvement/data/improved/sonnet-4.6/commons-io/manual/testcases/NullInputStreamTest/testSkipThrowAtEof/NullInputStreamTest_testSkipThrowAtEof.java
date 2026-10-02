package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

public class NullInputStreamTest_testSkipThrowAtEof {

    /**
     * A test-only subclass of NullInputStream that returns position-based byte values,
     * making it easy to verify which bytes were read.
     */
    private static final class TestNullInputStream extends NullInputStream {

        TestNullInputStream(final int size) {
            super(size);
        }

        TestNullInputStream(final int size, final boolean markSupported, final boolean throwEofException) {
            super(size, markSupported, throwEofException);
        }

        @Override
        protected int processByte() {
            return (int) getPosition() - 1;
        }

        @Override
        protected void processBytes(final byte[] bytes, final int offset, final int length) {
            final int startPos = (int) getPosition() - length;
            for (int i = offset; i < length; i++) {
                bytes[i] = (byte) (startPos + i);
            }
        }
    }

    /**
     * Verifies that skipping past the end of a NullInputStream configured with
     * throwEofException=true raises EOFException on the first overflow skip,
     * and IOException on any subsequent skip attempt.
     *
     * <p>Stream layout (size=10, positions 0–9):
     * <pre>
     *   read()  → byte 0   (position becomes 1)
     *   read()  → byte 1   (position becomes 2)
     *   skip(5) → 5 skipped (position becomes 7)
     *   read()  → byte 7   (position becomes 8)
     *   skip(5) → only 2 remain, so 2 skipped (position becomes 10 = EOF)
     *   skip(5) → EOF reached → EOFException thrown
     *   skip(5) → stream already exhausted → IOException thrown
     * </pre>
     */
    @Test
    void testSkipThrowAtEof() throws Exception {
        // markSupported=true, throwEofException=true: skipping past EOF must throw
        try (InputStream input = new TestNullInputStream(10, true, true)) {

            // Read two individual bytes to advance position to 2
            assertEquals(0, input.read(), "First read should return byte value 0");
            assertEquals(1, input.read(), "Second read should return byte value 1");

            // Skip 5 bytes (positions 2–6); all 5 are available, so exactly 5 are skipped
            assertEquals(5, input.skip(5), "Skip of 5 within bounds should skip exactly 5 bytes");

            // Read the byte at position 7
            assertEquals(7, input.read(), "Read after skip should return byte value 7 (position 7)");

            // Only 2 bytes remain (positions 8–9); requesting 5 returns 2 and advances to EOF
            assertEquals(2, input.skip(5), "Skip of 5 near EOF should return 2 (only 2 bytes remaining)");

            // Position is now at EOF; first skip past EOF throws EOFException (not generic IOException)
            final IOException eofOnFirstSkip = assertThrows(EOFException.class,
                    () -> input.skip(5),
                    "First skip at EOF should throw EOFException");
            assertTrue(StringUtils.isNotBlank(eofOnFirstSkip.getMessage()),
                    "EOFException should carry a non-blank message");

            // Stream is already exhausted; subsequent skips throw IOException
            final IOException ioOnSecondSkip = assertThrows(IOException.class,
                    () -> input.skip(5),
                    "Subsequent skip after EOF should throw IOException");
            assertTrue(StringUtils.isNotBlank(ioOnSecondSkip.getMessage()),
                    "IOException should carry a non-blank message");
        }
    }
}

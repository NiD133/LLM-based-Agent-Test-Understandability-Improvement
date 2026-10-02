package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.Reader;
import org.junit.jupiter.api.Test;

public class NullReaderTest_testSkip {

    /**
     * A NullReader that fills each character position with its 0-based index value,
     * making it easy to verify the current read position from the returned char value.
     */
    private static final class TestNullReader extends NullReader {

        TestNullReader(final int size) {
            super(size);
        }

        TestNullReader(final int size, final boolean markSupported, final boolean throwEofException) {
            super(size, markSupported, throwEofException);
        }

        @Override
        protected int processChar() {
            return (int) getPosition() - 1;
        }

        @Override
        protected void processChars(final char[] chars, final int offset, final int length) {
            final int startPos = (int) getPosition() - length;
            for (int i = offset; i < length; i++) {
                chars[i] = (char) (startPos + i);
            }
        }
    }

    /**
     * Verifies skip() behaviour across three distinct phases:
     *   1. Normal skipping within bounds — returns the requested count.
     *   2. Skipping past the end — returns only the remaining chars available.
     *   3. Skipping at/after EOF — returns -1 on first call, then throws IOException.
     *
     * The reader holds 10 characters (indices 0–9) and supports mark.
     */
    @Test
    void testSkip() throws Exception {
        // Reader emulates 10 characters, with mark support and no EOFException on end-of-file.
        try (Reader reader = new TestNullReader(10, true, false)) {

            // Advance position to index 2 by reading two characters individually.
            assertEquals(0, reader.read(), "First read should return character at index 0");
            assertEquals(1, reader.read(), "Second read should return character at index 1");

            // Skip 5 characters (positions 2–6); reader is now at index 7.
            assertEquals(5, reader.skip(5), "Skipping 5 chars within bounds should skip all 5");

            // Read confirms the reader is now at index 7.
            assertEquals(7, reader.read(), "Read after skip should return character at index 7");

            // Only 2 characters remain (indices 8–9), so skipping 5 returns 2.
            assertEquals(2, reader.skip(5), "Skipping 5 chars with only 2 remaining should return 2");

            // The reader is now at end-of-file; skip returns -1 (no EOFException configured).
            assertEquals(-1, reader.skip(5), "Skip at EOF should return -1");

            // Any subsequent skip after EOF must throw an IOException.
            final IOException eofError = assertThrows(IOException.class, () -> reader.skip(5));
            assertEquals("Skip after end of file", eofError.getMessage(),
                    "IOException message after EOF should be 'Skip after end of file'");
        }
    }
}

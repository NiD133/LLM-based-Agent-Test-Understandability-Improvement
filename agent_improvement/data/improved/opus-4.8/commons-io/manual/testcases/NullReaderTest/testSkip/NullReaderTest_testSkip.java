package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.Reader;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link NullReader#skip(long)}, covering normal skipping, skipping past
 * the end of the emulated content, and skipping after the end of file.
 */
public class NullReaderTest_testSkip {

    /**
     * A {@link NullReader} whose generated characters encode the reader position,
     * so that {@code read()} results can be asserted against expected positions.
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

    @Test
    void testSkip() throws Exception {
        // A 10-character reader that supports mark and returns -1 (rather than
        // throwing) the first time the end of file is reached.
        try (Reader reader = new TestNullReader(10, true, false)) {
            // Read the first two characters (positions move 0 -> 1 -> 2).
            assertEquals(0, reader.read(), "Read 1");
            assertEquals(1, reader.read(), "Read 2");

            // Skip 5 characters; all 5 are available (position moves 2 -> 7).
            assertEquals(5, reader.skip(5), "Skip 1");

            // Read the next character at position 7.
            assertEquals(7, reader.read(), "Read 3");

            // Only 2 characters remain before the end (position 8 -> 10),
            // so a request to skip 5 skips just 2.
            assertEquals(2, reader.skip(5), "Skip 2");

            // The end of file has now been reached; skipping returns -1.
            assertEquals(-1, reader.skip(5), "Skip 3 (EOF)");

            // Any further skip past the end of file is an error.
            final IOException e = assertThrows(IOException.class, () -> reader.skip(5));
            assertEquals("Skip after end of file", e.getMessage(),
                    "Skip after EOF IOException message");
        }
    }
}

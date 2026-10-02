package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.Reader;

import org.junit.jupiter.api.Test;

/**
 * Verifies the {@code mark(int)} / {@code reset()} contract of {@link NullReader}.
 */
public class NullReaderTest_testMarkAndReset {

    /**
     * A {@link NullReader} whose read methods return the position of each character,
     * so the test can assert exactly which character index was read.
     * <p>
     * Because {@link NullReader#read()} increments the position <em>before</em>
     * calling {@code processChar()}, returning {@code getPosition() - 1} yields the
     * zero-based index of the character that was just consumed (0, 1, 2, ...).
     * </p>
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
    void testMarkAndReset() throws Exception {
        final int readerSize = 100;
        final int readLimit = 10;
        final int charsReadBeforeMark = 3;
        final int charsReadAfterMark = 3;

        try (Reader reader = new TestNullReader(readerSize, true, false)) {
            assertTrue(reader.markSupported(), "Mark Should be Supported");

            // reset() before any mark() must fail.
            final IOException resetWithoutMark = assertThrows(IOException.class, reader::reset);
            assertEquals("No position has been marked", resetWithoutMark.getMessage(),
                    "No Mark IOException message");

            // Read a few characters; each read() returns its own index (0, 1, 2).
            int markedPosition = 0;
            for (; markedPosition < charsReadBeforeMark; markedPosition++) {
                assertEquals(markedPosition, reader.read(), "Read Before Mark [" + markedPosition + "]");
            }

            // Mark the current position (now == charsReadBeforeMark).
            reader.mark(readLimit);

            // Read further; indices continue from the marked position.
            for (int i = 0; i < charsReadAfterMark; i++) {
                assertEquals(markedPosition + i, reader.read(), "Read After Mark [" + i + "]");
            }

            // reset() rewinds to the marked position.
            reader.reset();

            // Reading from the marked position replays the same indices and then
            // advances one character past the read limit.
            for (int i = 0; i < readLimit + 1; i++) {
                assertEquals(markedPosition + i, reader.read(), "Read After Reset [" + i + "]");
            }

            // Having read past the read limit, reset() is no longer valid.
            final IOException resetPastLimit = assertThrows(IOException.class, reader::reset);
            assertEquals("Marked position [" + markedPosition + "] is no longer valid - passed the read limit ["
                    + readLimit + "]", resetPastLimit.getMessage(), "Read limit IOException message");
        }
    }
}

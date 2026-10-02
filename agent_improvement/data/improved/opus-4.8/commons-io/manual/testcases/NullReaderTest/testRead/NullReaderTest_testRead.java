package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.Reader;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link NullReader#read()}: sequential reads, end-of-file handling,
 * the error when reading past the end, and the reset performed by close().
 */
public class NullReaderTest_testRead {

    /**
     * A {@link NullReader} whose {@code read()} returns the index of the
     * character just read (0-based), so each emulated character has a
     * distinct, predictable value.
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
    void testRead() throws Exception {
        final int size = 5;
        final TestNullReader reader = new TestNullReader(size);

        // Each character read back equals its position index: 0, 1, 2, 3, 4.
        for (int i = 0; i < size; i++) {
            assertEquals(i, reader.read(), "Character at index [" + i + "]");
        }

        // The next read reports end of file with -1.
        assertEquals(-1, reader.read(), "End of file marker");

        // Reading past the end of file fails.
        final IOException readPastEof = assertThrows(IOException.class, reader::read,
                "Reading after end of file should throw");
        assertEquals("Read after end of file", readPastEof.getMessage());

        // Closing resets the reader's position back to the start.
        reader.close();
        assertEquals(0, reader.getPosition(), "Position after close");
    }
}

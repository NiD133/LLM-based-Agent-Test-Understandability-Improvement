package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.EOFException;
import java.io.Reader;

import org.junit.jupiter.api.Test;

/**
 * Verifies that a {@link NullReader} configured to throw an {@link EOFException}
 * does so once every emulated character has been read.
 */
public class NullReaderTest_testEOFException {

    /**
     * A {@link NullReader} whose {@code read()} returns each character's index
     * (position - 1) instead of the default zero, so successive reads yield
     * 0, 1, 2, ... up to the emulated size.
     */
    private static final class IndexReturningNullReader extends NullReader {

        IndexReturningNullReader(final int size, final boolean markSupported, final boolean throwEofException) {
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
    void testEOFException() throws Exception {
        final int emulatedSize = 2;
        final boolean markSupported = false;
        final boolean throwEofException = true;

        try (Reader reader = new IndexReturningNullReader(emulatedSize, markSupported, throwEofException)) {
            // Read every emulated character; each returns its own index.
            assertEquals(0, reader.read(), "First character (index 0)");
            assertEquals(1, reader.read(), "Second character (index 1)");

            // Reading past the end must throw EOFException, not return -1.
            assertThrows(EOFException.class, () -> reader.read());
        }
    }
}

package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class NullReaderTest_testEOFException {

    // Reader configuration constants for the EOFException scenario
    private static final int READER_SIZE = 2;
    private static final boolean MARK_NOT_SUPPORTED = false;
    private static final boolean THROW_EOF_EXCEPTION = true;

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
    @DisplayName("read() throws EOFException when throwEofException is true and end of stream is reached")
    void testEOFException() throws Exception {
        try (Reader reader = new TestNullReader(READER_SIZE, MARK_NOT_SUPPORTED, THROW_EOF_EXCEPTION)) {
            // processChar() returns (position - 1), so first read yields 0 and second yields 1
            assertEquals(0, reader.read(), "Read 1");
            assertEquals(1, reader.read(), "Read 2");
            assertThrows(EOFException.class, () -> reader.read());
        }
    }
}

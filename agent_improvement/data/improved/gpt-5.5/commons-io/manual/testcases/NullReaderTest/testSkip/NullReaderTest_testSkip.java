package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.Reader;

import org.junit.jupiter.api.Test;

public class NullReaderTest_testSkip {

    private static final int READER_SIZE = 10;
    private static final int SKIP_REQUEST = 5;

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
        try (Reader reader = new TestNullReader(READER_SIZE, true, false)) {
            assertEquals(0, reader.read(), "First read returns the first generated character");
            assertEquals(1, reader.read(), "Second read returns the next generated character");

            assertEquals(5, reader.skip(SKIP_REQUEST), "Skip advances by the requested amount");
            assertEquals(7, reader.read(), "Read resumes after the skipped characters");

            assertEquals(2, reader.skip(SKIP_REQUEST), "Skip stops at the end of the reader");
            assertEquals(-1, reader.skip(SKIP_REQUEST), "Skip at EOF returns -1");

            final IOException exception = assertThrows(IOException.class, () -> reader.skip(SKIP_REQUEST));
            assertEquals("Skip after end of file", exception.getMessage(), "Skip after EOF IOException message");
        }
    }
}

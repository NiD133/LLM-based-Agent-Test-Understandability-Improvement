package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.Reader;
import org.junit.jupiter.api.Test;

public class NullReaderTest_testMarkNotSupported {

    private static final String MARK_RESET_NOT_SUPPORTED = "mark/reset not supported";

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
    void testMarkNotSupported() throws Exception {
        final Reader reader = new TestNullReader(100, false, true);
        assertFalse(reader.markSupported(), "Mark Should NOT be Supported");

        final UnsupportedOperationException markException =
            assertThrows(UnsupportedOperationException.class, () -> reader.mark(5),
                "mark() should throw UnsupportedOperationException");
        assertEquals(MARK_RESET_NOT_SUPPORTED, markException.getMessage(), "mark() error message");

        final UnsupportedOperationException resetException =
            assertThrows(UnsupportedOperationException.class, () -> reader.reset(),
                "reset() should throw UnsupportedOperationException");
        assertEquals(MARK_RESET_NOT_SUPPORTED, resetException.getMessage(), "reset() error message");

        reader.close();
    }
}

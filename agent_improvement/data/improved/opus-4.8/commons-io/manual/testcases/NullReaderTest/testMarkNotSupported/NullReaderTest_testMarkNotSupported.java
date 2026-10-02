package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.Reader;

import org.junit.jupiter.api.Test;

/**
 * Verifies that a {@link NullReader} created with {@code markSupported == false}
 * rejects both {@code mark()} and {@code reset()} by throwing an
 * {@link UnsupportedOperationException} carrying the expected message.
 */
public class NullReaderTest_testMarkNotSupported {

    /** Message used by {@code java.io.InputStream.reset()} in OpenJDK 8.0.275-1. */
    private static final String MARK_RESET_NOT_SUPPORTED = "mark/reset not supported";

    /**
     * A concrete {@link NullReader} that emulates a reader of a given size.
     * The overrides only matter for read operations, which this test does not
     * exercise; they exist to match the reader used by the original test.
     */
    private static final class TestNullReader extends NullReader {

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
        // A reader explicitly created without mark support.
        final Reader reader = new TestNullReader(100, false, true);

        assertFalse(reader.markSupported(), "Mark should NOT be supported");

        // mark() must be rejected with the standard "mark/reset not supported" message.
        final UnsupportedOperationException markException =
                assertThrows(UnsupportedOperationException.class, () -> reader.mark(5),
                        "mark() should throw UnsupportedOperationException");
        assertEquals(MARK_RESET_NOT_SUPPORTED, markException.getMessage(), "mark() error message");

        // reset() must be rejected with the same message.
        final UnsupportedOperationException resetException =
                assertThrows(UnsupportedOperationException.class, reader::reset,
                        "reset() should throw UnsupportedOperationException");
        assertEquals(MARK_RESET_NOT_SUPPORTED, resetException.getMessage(), "reset() error message");

        reader.close();
    }
}

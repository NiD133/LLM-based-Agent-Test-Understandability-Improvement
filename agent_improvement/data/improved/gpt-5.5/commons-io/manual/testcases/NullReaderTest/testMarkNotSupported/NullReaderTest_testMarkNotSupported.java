package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.Reader;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class NullReaderTest_testMarkNotSupported {

    // Use the same message as in java.io.InputStream.reset() in OpenJDK 8.0.275-1.
    private static final String MARK_RESET_NOT_SUPPORTED = "mark/reset not supported";

    private static final int READER_SIZE = 100;
    private static final int READ_LIMIT = 5;

    private static final class TestNullReader extends NullReader {

        TestNullReader(final int size, final boolean markSupported, final boolean throwEofException) {
            super(size, markSupported, throwEofException);
        }
    }

    @Test
    void testMarkNotSupported() throws Exception {
        final Reader reader = new TestNullReader(READER_SIZE, false, true);

        assertFalse(reader.markSupported(), "Mark Should NOT be Supported");
        assertUnsupportedMarkReset(() -> reader.mark(READ_LIMIT), "mark() error message");
        assertUnsupportedMarkReset(reader::reset, "reset() error message");

        reader.close();
    }

    private static void assertUnsupportedMarkReset(final Executable action, final String message) {
        final UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class, action);
        assertEquals(MARK_RESET_NOT_SUPPORTED, exception.getMessage(), message);
    }
}

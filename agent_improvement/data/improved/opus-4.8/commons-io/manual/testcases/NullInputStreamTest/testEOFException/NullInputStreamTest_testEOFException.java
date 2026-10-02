package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.EOFException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link NullInputStream} throws an {@link EOFException} when a read
 * is attempted past the end of the emulated stream, provided the stream was
 * configured to throw on EOF.
 */
public class NullInputStreamTest_testEOFException {

    /**
     * A {@link NullInputStream} whose {@code read()} returns the (zero-based) index
     * of the byte just read, so the bytes of a stream of size N are 0, 1, ..., N-1.
     */
    private static final class IndexedNullInputStream extends NullInputStream {

        IndexedNullInputStream(final int size, final boolean markSupported, final boolean throwEofException) {
            super(size, markSupported, throwEofException);
        }

        @Override
        protected int processByte() {
            return (int) getPosition() - 1;
        }
    }

    @Test
    void testEOFException() throws Exception {
        final int size = 2;
        final boolean markSupported = false;
        final boolean throwEofException = true;

        try (InputStream input = new IndexedNullInputStream(size, markSupported, throwEofException)) {
            // The two bytes of the size-2 stream are read as 0 then 1.
            assertEquals(0, input.read(), "first byte");
            assertEquals(1, input.read(), "second byte");

            // Reading past the end throws because throwEofException is true.
            assertThrows(EOFException.class, () -> input.read());
        }
    }
}

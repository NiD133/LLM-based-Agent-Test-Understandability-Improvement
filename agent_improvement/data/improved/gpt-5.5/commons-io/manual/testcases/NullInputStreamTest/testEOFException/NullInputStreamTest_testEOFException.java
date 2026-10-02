package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.EOFException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

public class NullInputStreamTest_testEOFException {

    private static final int STREAM_SIZE = 2;
    private static final int FIRST_BYTE = 0;
    private static final int SECOND_BYTE = 1;

    private static final class SequentialByteNullInputStream extends NullInputStream {

        SequentialByteNullInputStream(final int size, final boolean markSupported, final boolean throwEofException) {
            super(size, markSupported, throwEofException);
        }

        @Override
        protected int processByte() {
            return (int) getPosition() - 1;
        }
    }

    @Test
    void testEOFException() throws Exception {
        try (InputStream input = new SequentialByteNullInputStream(STREAM_SIZE, false, true)) {
            assertEquals(FIRST_BYTE, input.read(), "Read 1");
            assertEquals(SECOND_BYTE, input.read(), "Read 2");
            assertThrows(EOFException.class, () -> input.read());
        }
    }
}

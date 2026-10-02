package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class NullInputStreamTest_testEOFException {

    /**
     * A NullInputStream subclass that returns position-based byte values,
     * allowing assertions to verify which byte was read and in what order.
     */
    private static final class TestNullInputStream extends NullInputStream {

        TestNullInputStream(final int size) {
            super(size);
        }

        TestNullInputStream(final int size, final boolean markSupported, final boolean throwEofException) {
            super(size, markSupported, throwEofException);
        }

        @Override
        protected int processByte() {
            return (int) getPosition() - 1;
        }

        @Override
        protected void processBytes(final byte[] bytes, final int offset, final int length) {
            final int startPos = (int) getPosition() - length;
            for (int i = offset; i < length; i++) {
                bytes[i] = (byte) (startPos + i);
            }
        }
    }

    @Test
    @DisplayName("read() throws EOFException after exhausting all bytes when throwEofException is enabled")
    void testEOFException() throws Exception {
        // Stream of size 2 with mark not supported and EOF exception enabled
        try (InputStream input = new TestNullInputStream(2, false, true)) {
            assertEquals(0, input.read(), "Read 1");
            assertEquals(1, input.read(), "Read 2");
            // Third read exceeds the stream size — must throw EOFException
            assertThrows(EOFException.class, () -> input.read());
        }
    }
}

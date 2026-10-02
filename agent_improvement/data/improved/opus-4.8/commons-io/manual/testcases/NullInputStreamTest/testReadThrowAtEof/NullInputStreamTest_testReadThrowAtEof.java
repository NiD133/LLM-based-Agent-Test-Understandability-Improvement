package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

/**
 * Tests that {@link NullInputStream}, when configured to throw on end-of-file,
 * raises an {@link EOFException} for every read attempt made once the emulated
 * content has been fully consumed.
 */
public class NullInputStreamTest_testReadThrowAtEof {

    /**
     * A {@link NullInputStream} that produces deterministic byte values so the
     * test can assert on the exact data returned by each read.
     * <p>
     * The byte read at position {@code i} (0-based) is {@code i}.
     * </p>
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
    void testReadThrowAtEof() throws Exception {
        final int size = 5;

        // markSupported = true, throwEofException = true: reads past EOF must throw.
        try (InputStream input = new TestNullInputStream(size, true, true)) {

            // Read every byte of the emulated content, checking the remaining
            // count and the returned value at each step.
            for (int i = 0; i < size; i++) {
                assertEquals(size - i, input.available(), "Remaining bytes before reading index [" + i + "]");
                assertEquals(i, input.read(), "Byte value at index [" + i + "]");
            }
            assertEquals(0, input.available(), "No bytes should remain after reading all content");

            // First read at EOF: an EOFException with a non-blank message is expected.
            final IOException firstEofError = assertThrows(EOFException.class, input::read);
            assertTrue(StringUtils.isNotBlank(firstEofError.getMessage()), "First EOF exception should carry a message");

            // Subsequent reads past EOF must keep throwing the same exception.
            final IOException secondEofError = assertThrows(EOFException.class, input::read);
            assertTrue(StringUtils.isNotBlank(secondEofError.getMessage()), "Second EOF exception should carry a message");

            // After closing, available() reports zero.
            input.close();
            assertEquals(0, input.available(), "No bytes should be available after close");
        }
    }
}

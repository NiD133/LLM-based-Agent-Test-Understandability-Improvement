package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

/**
 * Tests that a {@link NullInputStream} constructed with {@code markSupported=false}
 * correctly rejects both {@code mark()} and {@code reset()} with
 * {@link UnsupportedOperationException} carrying the standard JDK error message.
 */
public class NullInputStreamTest_testMarkNotSupported {

    /** Standard JDK error message used when mark/reset are not supported. */
    private static final String MARK_RESET_NOT_SUPPORTED = "mark/reset not supported";

    /**
     * Minimal {@link NullInputStream} subclass that supplies concrete byte values
     * so the stream behaves like a real data source during the test.
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

    /**
     * Verifies that when a {@link NullInputStream} is created with mark support disabled,
     * {@code markSupported()} returns {@code false} and both {@code mark(int)} and
     * {@code reset()} throw {@link UnsupportedOperationException} with the standard
     * "mark/reset not supported" message.
     */
    @Test
    void testMarkNotSupported() throws Exception {
        // Create a 100-byte stream with mark disabled and EOF exceptions enabled.
        try (InputStream input = new TestNullInputStream(100, false, true)) {

            // Confirm mark capability is advertised as unsupported.
            assertFalse(input.markSupported(), "Mark Should NOT be Supported");

            // mark(int) must throw because mark support is disabled.
            final UnsupportedOperationException markException =
                    assertThrows(UnsupportedOperationException.class, () -> input.mark(5));
            assertEquals(MARK_RESET_NOT_SUPPORTED, markException.getMessage(), "mark() error message");

            // reset() must throw for the same reason.
            final UnsupportedOperationException resetException =
                    assertThrows(UnsupportedOperationException.class, input::reset);
            assertEquals(MARK_RESET_NOT_SUPPORTED, resetException.getMessage(), "reset() error message");
        }
    }
}

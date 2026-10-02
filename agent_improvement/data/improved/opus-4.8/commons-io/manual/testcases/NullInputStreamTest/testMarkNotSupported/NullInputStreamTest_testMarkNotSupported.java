package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.InputStream;

import org.junit.jupiter.api.Test;

/**
 * Verifies that when a {@link NullInputStream} is created with mark support disabled,
 * the stream reports that marking is unsupported and that both {@code mark()} and
 * {@code reset()} reject calls with an {@link UnsupportedOperationException}.
 */
public class NullInputStreamTest_testMarkNotSupported {

    /**
     * Expected exception message, identical to the one used by
     * {@code java.io.InputStream.reset()} in OpenJDK 8.0.275-1.
     */
    private static final String MARK_RESET_NOT_SUPPORTED = "mark/reset not supported";

    /**
     * Minimal concrete subclass so {@link NullInputStream} can be instantiated.
     * The byte-generating hooks below are required overrides but are never exercised
     * by this test, which only inspects mark/reset behavior.
     */
    private static final class TestNullInputStream extends NullInputStream {

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
    void testMarkNotSupported() throws Exception {
        // Create a stream that explicitly does NOT support mark/reset.
        try (InputStream input = new TestNullInputStream(100, false, true)) {

            // The stream should advertise that marking is unsupported.
            assertFalse(input.markSupported(), "Mark Should NOT be Supported");

            // Calling mark() must fail with the standard "not supported" message.
            final UnsupportedOperationException markException =
                    assertThrows(UnsupportedOperationException.class, () -> input.mark(5));
            assertEquals(MARK_RESET_NOT_SUPPORTED, markException.getMessage(), "mark() error message");

            // Calling reset() must fail with the same standard message.
            final UnsupportedOperationException resetException =
                    assertThrows(UnsupportedOperationException.class, input::reset);
            assertEquals(MARK_RESET_NOT_SUPPORTED, resetException.getMessage(), "reset() error message");
        }
    }
}

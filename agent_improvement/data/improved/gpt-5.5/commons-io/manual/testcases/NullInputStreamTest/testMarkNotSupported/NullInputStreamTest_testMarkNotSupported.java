package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.InputStream;

import org.junit.jupiter.api.Test;

public class NullInputStreamTest_testMarkNotSupported {

    /**
     * Use the same message as in java.io.InputStream.reset() in OpenJDK 8.0.275-1.
     */
    private static final String MARK_RESET_NOT_SUPPORTED = "mark/reset not supported";

    @Test
    void testMarkNotSupported() throws Exception {
        try (InputStream input = new NullInputStream(100, false, true)) {
            assertFalse(input.markSupported(), "Mark Should NOT be Supported");

            final UnsupportedOperationException markException =
                    assertThrows(UnsupportedOperationException.class, () -> input.mark(5));
            assertEquals(MARK_RESET_NOT_SUPPORTED, markException.getMessage(), "mark() error message");

            final UnsupportedOperationException resetException =
                    assertThrows(UnsupportedOperationException.class, input::reset);
            assertEquals(MARK_RESET_NOT_SUPPORTED, resetException.getMessage(), "reset() error message");
        }
    }
}

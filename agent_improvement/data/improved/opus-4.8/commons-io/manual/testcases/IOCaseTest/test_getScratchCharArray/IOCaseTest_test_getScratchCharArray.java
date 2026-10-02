package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.Arrays;

import org.apache.commons.io.IOUtils.ScratchChars;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link IOUtils.ScratchChars}, the pooled, reusable {@code char[]} buffer.
 *
 * <p>The pool hands out a buffer when one is borrowed via {@code get()} and reclaims
 * it when the borrower closes it. This test verifies three guarantees:</p>
 * <ol>
 *   <li>a freshly borrowed buffer is always zero-filled;</li>
 *   <li>a second buffer borrowed while the first is still in use is a distinct array;</li>
 *   <li>once the first buffer is returned, the same array is handed out again, reset to zeros.</li>
 * </ol>
 */
public class IOCaseTest_test_getScratchCharArray {

    /** Asserts that every element of the buffer has been reset to zero. */
    private void assertAllZero(final char[] buffer) {
        for (final char element : buffer) {
            assertEquals(0, element);
        }
    }

    @Test
    void test_getScratchCharArray() {
        final char[] firstBuffer;

        // Borrow the first buffer: it must start out zero-filled.
        try (ScratchChars firstScratch = IOUtils.ScratchChars.get()) {
            firstBuffer = firstScratch.array();
            assertAllZero(firstBuffer);

            // Dirty the buffer so we can later confirm it gets reset before reuse.
            Arrays.fill(firstBuffer, (char) 1);

            // Borrow a second buffer while the first is still checked out.
            // (The try-with-resources isn't required here, but it mirrors normal usage.)
            try (ScratchChars secondScratch = IOUtils.ScratchChars.get()) {
                final char[] secondBuffer = secondScratch.array();
                assertAllZero(secondBuffer);
                // A concurrently borrowed buffer must be a different array.
                assertNotSame(firstBuffer, secondBuffer);
            }
        }

        // The first buffer has now been returned to the pool. Borrowing again should
        // hand back that same array, reset to zeros.
        try (ScratchChars reusedScratch = IOUtils.ScratchChars.get()) {
            final char[] reusedBuffer = reusedScratch.array();
            assertAllZero(reusedBuffer);
            assertSame(firstBuffer, reusedBuffer);
        }
    }
}

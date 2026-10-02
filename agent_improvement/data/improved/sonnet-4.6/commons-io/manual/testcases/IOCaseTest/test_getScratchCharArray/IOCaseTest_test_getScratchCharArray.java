package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import java.util.Arrays;
import org.apache.commons.io.IOUtils.ScratchChars;
import org.junit.jupiter.api.Test;

public class IOCaseTest_test_getScratchCharArray {

    /** Verifies that every element in the array is zero (i.e. the buffer was properly cleared). */
    private void assertAllElementsAreZero(final char[] arr) {
        for (final char element : arr) {
            assertEquals(0, element);
        }
    }

    @Test
    void test_getScratchCharArray() {
        final char[] firstArray;

        // Acquire a scratch char buffer; it must be zeroed on first use.
        try (ScratchChars firstScratch = IOUtils.ScratchChars.get()) {
            firstArray = firstScratch.array();
            assertAllElementsAreZero(firstArray);

            // Dirty the buffer so we can later confirm it was reset on release.
            Arrays.fill(firstArray, (char) 1);

            // While firstScratch is still open, requesting another buffer must
            // return a completely separate array (the pool cannot reuse a live buffer).
            try (ScratchChars secondScratch = IOUtils.ScratchChars.get()) {
                final char[] secondArray = secondScratch.array();
                assertAllElementsAreZero(secondArray);
                assertNotSame(firstArray, secondArray);
            }
        }

        // After firstScratch is closed the pool resets the buffer and returns the
        // same underlying array instance, now zeroed out again.
        try (ScratchChars reusedScratch = IOUtils.ScratchChars.get()) {
            final char[] reusedArray = reusedScratch.array();
            assertAllElementsAreZero(reusedArray);
            assertSame(firstArray, reusedArray);
        }
    }
}

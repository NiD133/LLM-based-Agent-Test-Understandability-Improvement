package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.Arrays;

import org.apache.commons.io.IOUtils.ScratchChars;
import org.junit.jupiter.api.Test;

public class IOCaseTest_test_getScratchCharArray {

    private void assertZeroFilled(final char[] array) {
        for (final char value : array) {
            assertEquals(0, value);
        }
    }

    @Test
    void test_getScratchCharArray() {
        final char[] firstBorrowedArray;

        try (ScratchChars firstScratch = IOUtils.ScratchChars.get()) {
            firstBorrowedArray = firstScratch.array();
            assertZeroFilled(firstBorrowedArray);

            Arrays.fill(firstBorrowedArray, (char) 1);

            try (ScratchChars concurrentScratch = IOUtils.ScratchChars.get()) {
                final char[] concurrentArray = concurrentScratch.array();
                assertZeroFilled(concurrentArray);
                assertNotSame(firstBorrowedArray, concurrentArray);
            }
        }

        try (ScratchChars reusedScratch = IOUtils.ScratchChars.get()) {
            final char[] reusedArray = reusedScratch.array();
            assertZeroFilled(reusedArray);
            assertSame(firstBorrowedArray, reusedArray);
        }
    }
}

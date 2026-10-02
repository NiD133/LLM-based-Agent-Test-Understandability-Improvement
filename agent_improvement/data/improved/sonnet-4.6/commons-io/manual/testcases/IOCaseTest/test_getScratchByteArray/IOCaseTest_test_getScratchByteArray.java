package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.Arrays;

import org.apache.commons.io.IOUtils.ScratchBytes;
import org.junit.jupiter.api.Test;

public class IOCaseTest_test_getScratchByteArray {

    /**
     * Asserts every element in the array is zero, confirming the scratch buffer
     * was properly reset before being handed out.
     */
    private void assertAllZero(final byte[] arr) {
        for (final byte e : arr) {
            assertEquals(0, e);
        }
    }

    @Test
    void test_getScratchByteArray() {
        final byte[] firstArray;

        // Acquire the first scratch buffer and dirty it so we can detect reuse later.
        try (ScratchBytes scratch = IOUtils.ScratchBytes.get()) {
            firstArray = scratch.array();
            assertAllZero(firstArray);
            Arrays.fill(firstArray, (byte) 1);

            // While the first buffer is still open, requesting a second one must return
            // a distinct object backed by its own zeroed array.
            try (ScratchBytes scratch2 = IOUtils.ScratchBytes.get()) {
                assertNotSame(scratch, scratch2);
                final byte[] secondArray = scratch2.array();
                assertAllZero(secondArray);
                assertNotSame(firstArray, secondArray);
            }
        }

        // After closing the first buffer, the pool should reclaim and reset it.
        // The next acquire must return the same backing array, now zeroed again.
        try (ScratchBytes scratch = IOUtils.ScratchBytes.get()) {
            final byte[] reusedArray = scratch.array();
            assertAllZero(reusedArray);
            assertSame(firstArray, reusedArray);
        }
    }
}

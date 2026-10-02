package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.Arrays;

import org.apache.commons.io.IOUtils.ScratchBytes;
import org.junit.jupiter.api.Test;

/**
 * Tests the pooling behaviour of {@link IOUtils.ScratchBytes}.
 */
public class IOCaseTest_test_getScratchByteArray {

    /** Asserts that every byte in the given array is zero. */
    private void assertAllZero(final byte[] array) {
        for (final byte value : array) {
            assertEquals(0, value);
        }
    }

    @Test
    void test_getScratchByteArray() {
        final byte[] firstArray;

        // Borrow a scratch buffer. It must start out zeroed.
        try (ScratchBytes scratch = IOUtils.ScratchBytes.get()) {
            firstArray = scratch.array();
            assertAllZero(firstArray);

            // Dirty the buffer so we can later verify it gets reset before reuse.
            Arrays.fill(firstArray, (byte) 1);

            // While the first buffer is still in use, borrow a second one.
            // (The try-with-resources here is unnecessary for the test, but
            //  follows the normal usage pattern.)
            try (ScratchBytes scratch2 = IOUtils.ScratchBytes.get()) {
                assertNotSame(scratch, scratch2);

                final byte[] secondArray = scratch2.array();
                assertAllZero(secondArray);
                // The two concurrently-held buffers must be distinct.
                assertNotSame(firstArray, secondArray);
            }
        }

        // After being released, the first buffer should be reset and handed back.
        try (ScratchBytes scratch = IOUtils.ScratchBytes.get()) {
            final byte[] reusedArray = scratch.array();
            assertAllZero(reusedArray);
            // Same instance as before, proving the pool reuses it.
            assertSame(firstArray, reusedArray);
        }
    }
}

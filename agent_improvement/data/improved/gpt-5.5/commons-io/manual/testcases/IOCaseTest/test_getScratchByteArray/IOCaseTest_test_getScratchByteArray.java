package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.Arrays;

import org.apache.commons.io.IOUtils.ScratchBytes;
import org.junit.jupiter.api.Test;

public class IOCaseTest_test_getScratchByteArray {

    private static final byte NON_ZERO_VALUE = 1;

    private void assertArrayIsCleared(final byte[] array) {
        for (final byte element : array) {
            assertEquals(0, element);
        }
    }

    @Test
    void test_getScratchByteArray() {
        final byte[] firstArray;

        try (ScratchBytes firstScratch = IOUtils.ScratchBytes.get()) {
            firstArray = firstScratch.array();
            assertArrayIsCleared(firstArray);

            Arrays.fill(firstArray, NON_ZERO_VALUE);

            try (ScratchBytes nestedScratch = IOUtils.ScratchBytes.get()) {
                assertNotSame(firstScratch, nestedScratch);

                final byte[] nestedArray = nestedScratch.array();
                assertArrayIsCleared(nestedArray);
                assertNotSame(firstArray, nestedArray);
            }
        }

        try (ScratchBytes reusedScratch = IOUtils.ScratchBytes.get()) {
            final byte[] reusedArray = reusedScratch.array();
            assertArrayIsCleared(reusedArray);
            assertSame(firstArray, reusedArray);
        }
    }
}

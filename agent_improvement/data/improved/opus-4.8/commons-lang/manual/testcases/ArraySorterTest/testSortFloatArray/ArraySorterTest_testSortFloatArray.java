package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ArraySorter#sort(float[])}.
 */
public class ArraySorterTest_testSortFloatArray extends AbstractLangTest {

    @Test
    void testSortFloatArray() {
        // Given an unsorted array, and the same values sorted by java.util.Arrays as the expected baseline.
        final float[] unsorted = { 2, 1 };
        final float[] expected = unsorted.clone();
        Arrays.sort(expected);

        // When sorting via ArraySorter, the result should match the baseline ascending order.
        assertArrayEquals(expected, ArraySorter.sort(unsorted));

        // And a null array should be returned as null.
        assertNull(ArraySorter.sort((float[]) null));
    }
}

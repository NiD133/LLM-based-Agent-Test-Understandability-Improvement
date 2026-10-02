package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ArraySorter#sort(long[])}.
 */
public class ArraySorterTest_testSortLongArray extends AbstractLangTest {

    @Test
    void testSortLongArray() {
        // Given an unsorted array and the expected result produced by Arrays.sort.
        final long[] unsorted = { 2, 1 };
        final long[] expected = unsorted.clone();
        Arrays.sort(expected);

        // When the array is sorted, it should match the ascending order.
        assertArrayEquals(expected, ArraySorter.sort(unsorted));

        // And a null array should be returned unchanged (as null).
        assertNull(ArraySorter.sort((long[]) null));
    }
}

package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ArraySorter#sort(int[])}.
 */
public class ArraySorterTest_testSortIntArray extends AbstractLangTest {

    @Test
    void testSortIntArray() {
        // Given an unsorted int array, ArraySorter.sort should return it in ascending order,
        // matching the result of java.util.Arrays.sort on an equivalent array.
        final int[] unsorted = { 2, 1 };
        final int[] expected = unsorted.clone();
        Arrays.sort(expected);

        assertArrayEquals(expected, ArraySorter.sort(unsorted));

        // A null array argument is passed through and returned as null.
        assertNull(ArraySorter.sort((int[]) null));
    }
}

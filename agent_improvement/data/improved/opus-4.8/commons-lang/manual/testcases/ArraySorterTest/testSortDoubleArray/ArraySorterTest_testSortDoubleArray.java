package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ArraySorter#sort(double[])}.
 */
public class ArraySorterTest_testSortDoubleArray extends AbstractLangTest {

    @Test
    void testSortDoubleArray() {
        // An unsorted array and the same values sorted ascending, used as the expected result.
        final double[] unsorted = { 2, 1 };
        final double[] expectedSorted = unsorted.clone();
        Arrays.sort(expectedSorted);

        // ArraySorter.sort should sort the array in place and return it.
        assertArrayEquals(expectedSorted, ArraySorter.sort(unsorted));

        // A null input should be returned unchanged (null).
        assertNull(ArraySorter.sort((double[]) null));
    }
}

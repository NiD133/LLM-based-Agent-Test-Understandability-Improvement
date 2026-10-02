package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

public class ArraySorterTest_testSortFloatArray extends AbstractLangTest {

    @Test
    void testSortFloatArray() {
        // Verify that sort() returns the array in ascending order
        final float[] unsorted = { 2, 1 };
        final float[] inputToSort = unsorted.clone();
        Arrays.sort(unsorted); // compute expected ascending order
        assertArrayEquals(unsorted, ArraySorter.sort(inputToSort),
                "sort(float[]) should return the array sorted in ascending order");

        // Verify that sort() returns null when given a null array
        assertNull(ArraySorter.sort((float[]) null),
                "sort(null) should return null");
    }
}

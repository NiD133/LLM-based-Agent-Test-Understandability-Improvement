package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class ArraySorterTest_testSortIntArray extends AbstractLangTest {

    @Test
    void testSortIntArray() {
        final int[] expectedSortedArray = { 2, 1 };
        final int[] arrayToSort = expectedSortedArray.clone();

        Arrays.sort(expectedSortedArray);

        assertArrayEquals(expectedSortedArray, ArraySorter.sort(arrayToSort));
        assertNull(ArraySorter.sort((int[]) null));
    }
}

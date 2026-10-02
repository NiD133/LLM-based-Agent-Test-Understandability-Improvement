package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class ArraySorterTest_testSortLongArray extends AbstractLangTest {

    @Test
    void testSortLongArray_sortsElementsInAscendingOrder() {
        final long[] unsortedInput = { 2, 1 };
        final long[] expectedSorted = { 1, 2 };

        long[] result = ArraySorter.sort(unsortedInput);

        assertArrayEquals(expectedSorted, result);
    }

    @Test
    void testSortLongArray_returnsNullForNullInput() {
        assertNull(ArraySorter.sort((long[]) null));
    }
}

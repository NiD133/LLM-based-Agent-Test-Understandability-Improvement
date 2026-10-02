package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class ArraySorterTest_testSortShortArray extends AbstractLangTest {

    @Test
    void testSortShortArray_sortsElementsInAscendingOrder() {
        final short[] unsorted = {2, 1};
        final short[] input = unsorted.clone();

        final short[] expectedSorted = unsorted.clone();
        Arrays.sort(expectedSorted);

        assertArrayEquals(expectedSorted, ArraySorter.sort(input));
    }

    @Test
    void testSortShortArray_returnsNullWhenInputIsNull() {
        assertNull(ArraySorter.sort((short[]) null));
    }
}

package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

public class ArraySorterTest_testSortIntArray extends AbstractLangTest {

    @Test
    void testSortIntArray_sortsElementsInAscendingOrder() {
        final int[] unsorted = { 2, 1 };
        final int[] input = unsorted.clone();
        Arrays.sort(unsorted);
        final int[] expected = unsorted;

        final int[] result = ArraySorter.sort(input);

        assertArrayEquals(expected, result);
    }

    @Test
    void testSortIntArray_returnsNullWhenInputIsNull() {
        assertNull(ArraySorter.sort((int[]) null));
    }
}

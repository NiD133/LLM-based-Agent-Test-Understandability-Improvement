package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class ArraySorterTest_testSortDoubleArray extends AbstractLangTest {

    @Test
    void testSortDoubleArray_sortsElementsInAscendingOrder() {
        final double[] unsorted = { 2, 1 };
        final double[] expected = unsorted.clone();
        Arrays.sort(expected);

        final double[] actual = ArraySorter.sort(unsorted);

        assertArrayEquals(expected, actual);
    }

    @Test
    void testSortDoubleArray_returnsNullWhenInputIsNull() {
        assertNull(ArraySorter.sort((double[]) null));
    }
}

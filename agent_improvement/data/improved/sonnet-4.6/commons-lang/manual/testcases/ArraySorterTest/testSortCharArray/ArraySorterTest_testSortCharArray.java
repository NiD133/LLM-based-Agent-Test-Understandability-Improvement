package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class ArraySorterTest_testSortCharArray extends AbstractLangTest {

    @Test
    void testSortCharArray_sortsElementsInAscendingOrder() {
        final char[] unsorted = { 2, 1 };
        final char[] expected = unsorted.clone();
        Arrays.sort(expected);

        final char[] result = ArraySorter.sort(unsorted);

        assertArrayEquals(expected, result);
    }

    @Test
    void testSortCharArray_returnsNullWhenInputIsNull() {
        assertNull(ArraySorter.sort((char[]) null));
    }
}

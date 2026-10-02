package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class ArraySorterTest_testSortShortArray extends AbstractLangTest {

    @Test
    void testSortShortArray() {
        final short[] expectedSortedArray = { 2, 1 };
        final short[] arrayToSort = expectedSortedArray.clone();

        Arrays.sort(expectedSortedArray);

        assertArrayEquals(expectedSortedArray, ArraySorter.sort(arrayToSort));
        assertNull(ArraySorter.sort((short[]) null));
    }
}

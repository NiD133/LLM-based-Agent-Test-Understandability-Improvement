package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class ArraySorterTest_testSortLongArray extends AbstractLangTest {

    @Test
    void testSortLongArray() {
        final long[] expectedSortedValues = { 2, 1 };
        final long[] valuesToSort = expectedSortedValues.clone();

        Arrays.sort(expectedSortedValues);

        assertArrayEquals(expectedSortedValues, ArraySorter.sort(valuesToSort));
        assertNull(ArraySorter.sort((long[]) null));
    }
}

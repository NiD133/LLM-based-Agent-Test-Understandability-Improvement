package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class ArraySorterTest_testSortObjects extends AbstractLangTest {

    @Test
    void testSortObjects() {
        final String[] expectedSortedArray = ArrayUtils.toArray("foo", "bar");
        final String[] arrayToSort = expectedSortedArray.clone();

        Arrays.sort(expectedSortedArray);

        assertArrayEquals(expectedSortedArray, ArraySorter.sort(arrayToSort));
        assertNull(ArraySorter.sort((String[]) null));
    }
}

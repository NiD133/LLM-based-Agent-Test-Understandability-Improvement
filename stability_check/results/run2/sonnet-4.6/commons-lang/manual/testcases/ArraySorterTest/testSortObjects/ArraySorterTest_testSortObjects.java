package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

public class ArraySorterTest_testSortObjects extends AbstractLangTest {

    @Test
    void testSortObjects_sortsByNaturalOrder() {
        final String[] unsortedStrings = ArrayUtils.toArray("foo", "bar");
        final String[] arrayToSort = unsortedStrings.clone();

        final String[] expectedSorted = unsortedStrings.clone();
        Arrays.sort(expectedSorted);

        assertArrayEquals(expectedSorted, ArraySorter.sort(arrayToSort));
    }

    @Test
    void testSortObjects_nullInputReturnsNull() {
        assertNull(ArraySorter.sort((String[]) null));
    }
}

package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

public class ArraySorterTest_testSortComparable extends AbstractLangTest {

    @Test
    void testSortComparable_sortsArrayUsingComparator() {
        final String[] inputArray = ArrayUtils.toArray("foo", "bar");
        final String[] expectedSorted = inputArray.clone();
        Arrays.sort(expectedSorted);

        final String[] result = ArraySorter.sort(inputArray, String::compareTo);

        assertArrayEquals(expectedSorted, result);
    }

    @Test
    void testSortComparable_returnsNullForNullInput() {
        assertNull(ArraySorter.sort((String[]) null));
    }
}

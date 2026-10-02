package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class ArraySorterTest_testSortObjects extends AbstractLangTest {

    @Test
    void testSortObjects_sortsSortableArrayInAscendingOrder() {
        final String[] unsortedInput = ArrayUtils.toArray("foo", "bar");
        final String[] copyOfInput = unsortedInput.clone();

        // Compute the expected sorted order using the standard library
        Arrays.sort(unsortedInput);
        final String[] expectedSorted = unsortedInput;

        assertArrayEquals(expectedSorted, ArraySorter.sort(copyOfInput));
    }

    @Test
    void testSortObjects_returnsNullForNullInput() {
        assertNull(ArraySorter.sort((String[]) null));
    }
}

package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ArraySorter#sort(Object[])}, the generic object-array overload.
 */
public class ArraySorterTest_testSortObjects extends AbstractLangTest {

    @Test
    void testSortObjects() {
        // Given an unsorted array and a reference sorted with the JDK's Arrays.sort.
        final String[] unsortedArray = ArrayUtils.toArray("foo", "bar");
        final String[] expectedSortedArray = unsortedArray.clone();
        Arrays.sort(expectedSortedArray);

        // When ArraySorter.sort orders the array, it must match the JDK ordering.
        assertArrayEquals(expectedSortedArray, ArraySorter.sort(unsortedArray));

        // And a null array is returned unchanged (as null) rather than throwing.
        assertNull(ArraySorter.sort((String[]) null));
    }
}

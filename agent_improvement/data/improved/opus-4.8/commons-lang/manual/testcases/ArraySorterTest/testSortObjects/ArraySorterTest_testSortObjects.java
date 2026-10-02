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
        // Arrange: an unsorted array, plus the same elements sorted via java.util.Arrays for comparison.
        final String[] unsorted = ArrayUtils.toArray("foo", "bar");
        final String[] expected = unsorted.clone();
        Arrays.sort(expected);

        // Act & Assert: ArraySorter.sort produces the same ascending order as Arrays.sort.
        assertArrayEquals(expected, ArraySorter.sort(unsorted));

        // A null array is returned unchanged (as null) rather than throwing.
        assertNull(ArraySorter.sort((String[]) null));
    }
}

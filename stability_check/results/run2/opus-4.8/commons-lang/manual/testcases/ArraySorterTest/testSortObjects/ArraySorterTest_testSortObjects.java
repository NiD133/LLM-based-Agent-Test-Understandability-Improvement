package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ArraySorter#sort(Object[])} with object (String) arrays.
 */
public class ArraySorterTest_testSortObjects extends AbstractLangTest {

    @Test
    void testSortObjects() {
        // Given an unsorted String array, ArraySorter.sort should return it in ascending order.
        final String[] unsorted = ArrayUtils.toArray("foo", "bar");
        final String[] expected = {"bar", "foo"};

        assertArrayEquals(expected, ArraySorter.sort(unsorted));

        // A null array should be returned unchanged (null).
        assertNull(ArraySorter.sort((String[]) null));
    }
}

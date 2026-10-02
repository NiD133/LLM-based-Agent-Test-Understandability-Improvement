package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ArraySorter#sort(Object[], java.util.Comparator)}, the overload
 * that sorts an object array using a supplied {@link java.util.Comparator}.
 */
public class ArraySorterTest_testSortComparable extends AbstractLangTest {

    @Test
    void testSortComparable() {
        // Sort a copy with the comparator-based overload and confirm the result
        // matches the same elements sorted by Arrays.sort (natural ordering).
        final String[] unsorted = ArrayUtils.toArray("foo", "bar");
        final String[] expected = unsorted.clone();
        Arrays.sort(expected);

        final String[] actual = ArraySorter.sort(unsorted, String::compareTo);
        assertArrayEquals(expected, actual);

        // A null array is returned unchanged (as null) rather than throwing.
        assertNull(ArraySorter.sort((String[]) null));
    }
}

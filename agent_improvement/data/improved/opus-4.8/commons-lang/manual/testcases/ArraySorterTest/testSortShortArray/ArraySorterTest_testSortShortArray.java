package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ArraySorter#sort(short[])}.
 */
public class ArraySorterTest_testSortShortArray extends AbstractLangTest {

    @Test
    void testSortShortArray() {
        // Given an unsorted array and a reference copy sorted by the JDK.
        final short[] unsorted = { 2, 1 };
        final short[] expected = unsorted.clone();
        Arrays.sort(expected);

        // ArraySorter.sort sorts the array into ascending order in place and returns it.
        assertArrayEquals(expected, ArraySorter.sort(unsorted));

        // A null array is returned unchanged (as null).
        assertNull(ArraySorter.sort((short[]) null));
    }
}

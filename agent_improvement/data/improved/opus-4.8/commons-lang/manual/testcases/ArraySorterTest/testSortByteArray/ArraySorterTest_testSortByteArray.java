package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ArraySorter#sort(byte[])}.
 */
public class ArraySorterTest_testSortByteArray extends AbstractLangTest {

    @Test
    void testSortByteArray() {
        // Given an unsorted byte array, ArraySorter.sort should return it sorted
        // into ascending order, matching the result of java.util.Arrays.sort.
        final byte[] unsorted = { 2, 1 };
        final byte[] expected = unsorted.clone();
        Arrays.sort(expected);

        assertArrayEquals(expected, ArraySorter.sort(unsorted));

        // A null array is returned unchanged (as null).
        assertNull(ArraySorter.sort((byte[]) null));
    }
}

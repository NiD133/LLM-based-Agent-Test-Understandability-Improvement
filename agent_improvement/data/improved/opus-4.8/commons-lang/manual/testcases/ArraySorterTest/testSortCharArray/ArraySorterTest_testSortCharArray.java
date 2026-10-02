package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ArraySorter#sort(char[])}.
 */
public class ArraySorterTest_testSortCharArray extends AbstractLangTest {

    @Test
    void testSortCharArray() {
        // Given an unsorted char array and a reference copy sorted by java.util.Arrays.
        final char[] unsorted = { 2, 1 };
        final char[] expectedSorted = unsorted.clone();
        Arrays.sort(expectedSorted);

        // When ArraySorter.sort is called, it should sort into the same ascending order.
        assertArrayEquals(expectedSorted, ArraySorter.sort(unsorted));

        // And a null input should return null rather than throwing.
        assertNull(ArraySorter.sort((char[]) null));
    }
}

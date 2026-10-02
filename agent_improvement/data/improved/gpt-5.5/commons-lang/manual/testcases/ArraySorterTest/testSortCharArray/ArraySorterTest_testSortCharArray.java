package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class ArraySorterTest_testSortCharArray extends AbstractLangTest {

    @Test
    void testSortCharArray() {
        final char[] expectedSortedChars = { 2, 1 };
        final char[] charsToSort = expectedSortedChars.clone();

        Arrays.sort(expectedSortedChars);

        assertArrayEquals(expectedSortedChars, ArraySorter.sort(charsToSort));
        assertNull(ArraySorter.sort((char[]) null));
    }
}

package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.jupiter.api.Test;

public class IOCaseTest_test_checkIndexOf_functionality {

    private static final boolean WINDOWS = File.separatorChar == '\\';

    private static final String HAYSTACK = "ABCDEFGHIJ";

    @Test
    void test_checkIndexOf_searchAtStart() {
        // Single character at position 0
        assertEquals(0, IOCase.SENSITIVE.checkIndexOf(HAYSTACK, 0, "A"));
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf(HAYSTACK, 1, "A"));

        // Two characters starting at position 0
        assertEquals(0, IOCase.SENSITIVE.checkIndexOf(HAYSTACK, 0, "AB"));
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf(HAYSTACK, 1, "AB"));

        // Three characters starting at position 0
        assertEquals(0, IOCase.SENSITIVE.checkIndexOf(HAYSTACK, 0, "ABC"));
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf(HAYSTACK, 1, "ABC"));
    }

    @Test
    void test_checkIndexOf_searchInMiddle() {
        // Single character in the middle
        assertEquals(3, IOCase.SENSITIVE.checkIndexOf(HAYSTACK, 0, "D"));
        assertEquals(3, IOCase.SENSITIVE.checkIndexOf(HAYSTACK, 3, "D"));
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf(HAYSTACK, 4, "D"));

        // Two characters in the middle
        assertEquals(3, IOCase.SENSITIVE.checkIndexOf(HAYSTACK, 0, "DE"));
        assertEquals(3, IOCase.SENSITIVE.checkIndexOf(HAYSTACK, 3, "DE"));
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf(HAYSTACK, 4, "DE"));

        // Three characters in the middle
        assertEquals(3, IOCase.SENSITIVE.checkIndexOf(HAYSTACK, 0, "DEF"));
        assertEquals(3, IOCase.SENSITIVE.checkIndexOf(HAYSTACK, 3, "DEF"));
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf(HAYSTACK, 4, "DEF"));
    }

    @Test
    void test_checkIndexOf_searchAtEnd() {
        // Single character at the last position
        assertEquals(9, IOCase.SENSITIVE.checkIndexOf(HAYSTACK, 0, "J"));
        assertEquals(9, IOCase.SENSITIVE.checkIndexOf(HAYSTACK, 8, "J"));
        assertEquals(9, IOCase.SENSITIVE.checkIndexOf(HAYSTACK, 9, "J"));

        // Two characters ending at the last position
        assertEquals(8, IOCase.SENSITIVE.checkIndexOf(HAYSTACK, 0, "IJ"));
        assertEquals(8, IOCase.SENSITIVE.checkIndexOf(HAYSTACK, 8, "IJ"));
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf(HAYSTACK, 9, "IJ"));

        // Three characters ending at the last position
        assertEquals(7, IOCase.SENSITIVE.checkIndexOf(HAYSTACK, 6, "HIJ"));
        assertEquals(7, IOCase.SENSITIVE.checkIndexOf(HAYSTACK, 7, "HIJ"));
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf(HAYSTACK, 8, "HIJ"));
    }

    @Test
    void test_checkIndexOf_searchNotFound() {
        // Search string present but not as a contiguous sequence
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf(HAYSTACK, 0, "DED"));
    }

    @Test
    void test_checkIndexOf_searchLongerThanHaystack() {
        // Search string is longer than the string being searched
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf("DEF", 0, HAYSTACK));
    }

    @Test
    void test_checkIndexOf_nullInputs() {
        // Null search string returns -1
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf("ABC", 0, null));
        // Null haystack returns -1
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf(null, 0, "ABC"));
        // Both null returns -1
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf(null, 0, null));
    }
}

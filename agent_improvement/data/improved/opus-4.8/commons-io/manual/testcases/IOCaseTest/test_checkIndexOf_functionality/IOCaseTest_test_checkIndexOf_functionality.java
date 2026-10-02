package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link IOCase#checkIndexOf(String, int, String)} for the case-sensitive
 * comparison rule.
 * <p>
 * Each assertion searches the fixed haystack {@code "ABCDEFGHIJ"} (indices 0..9) for a
 * needle, starting the scan at a given index. The method returns the first index at or
 * after the start index where the needle matches, or {@code -1} when it cannot match.
 * </p>
 */
public class IOCaseTest_test_checkIndexOf_functionality {

    /** The haystack searched in every match scenario; characters map directly to their index. */
    private static final String HAYSTACK = "ABCDEFGHIJ";

    /** Convenience wrapper to keep the assertions focused on (start index, needle, expected). */
    private static int indexOf(final String haystack, final int fromIndex, final String needle) {
        return IOCase.SENSITIVE.checkIndexOf(haystack, fromIndex, needle);
    }

    @Test
    void test_checkIndexOf_functionality() {
        // Needle located at the very start (index 0); found when scanning from 0, missed once we start past it.
        assertEquals(0, indexOf(HAYSTACK, 0, "A"));
        assertEquals(-1, indexOf(HAYSTACK, 1, "A"));
        assertEquals(0, indexOf(HAYSTACK, 0, "AB"));
        assertEquals(-1, indexOf(HAYSTACK, 1, "AB"));
        assertEquals(0, indexOf(HAYSTACK, 0, "ABC"));
        assertEquals(-1, indexOf(HAYSTACK, 1, "ABC"));

        // Needle located in the middle (index 3); found when the start index is at or before 3, missed beyond it.
        assertEquals(3, indexOf(HAYSTACK, 0, "D"));
        assertEquals(3, indexOf(HAYSTACK, 3, "D"));
        assertEquals(-1, indexOf(HAYSTACK, 4, "D"));
        assertEquals(3, indexOf(HAYSTACK, 0, "DE"));
        assertEquals(3, indexOf(HAYSTACK, 3, "DE"));
        assertEquals(-1, indexOf(HAYSTACK, 4, "DE"));
        assertEquals(3, indexOf(HAYSTACK, 0, "DEF"));
        assertEquals(3, indexOf(HAYSTACK, 3, "DEF"));
        assertEquals(-1, indexOf(HAYSTACK, 4, "DEF"));

        // Needle located at the end; found while the start index still leaves room for the needle, missed otherwise.
        assertEquals(9, indexOf(HAYSTACK, 0, "J"));
        assertEquals(9, indexOf(HAYSTACK, 8, "J"));
        assertEquals(9, indexOf(HAYSTACK, 9, "J"));
        assertEquals(8, indexOf(HAYSTACK, 0, "IJ"));
        assertEquals(8, indexOf(HAYSTACK, 8, "IJ"));
        assertEquals(-1, indexOf(HAYSTACK, 9, "IJ"));
        assertEquals(7, indexOf(HAYSTACK, 6, "HIJ"));
        assertEquals(7, indexOf(HAYSTACK, 7, "HIJ"));
        assertEquals(-1, indexOf(HAYSTACK, 8, "HIJ"));

        // Needle whose characters appear in the haystack but never as a contiguous run.
        assertEquals(-1, indexOf(HAYSTACK, 0, "DED"));

        // Needle longer than the haystack can never match.
        assertEquals(-1, indexOf("DEF", 0, "ABCDEFGHIJ"));

        // Null haystack or null needle always yields -1.
        assertEquals(-1, indexOf("ABC", 0, null));
        assertEquals(-1, indexOf(null, 0, "ABC"));
        assertEquals(-1, indexOf(null, 0, null));
    }
}

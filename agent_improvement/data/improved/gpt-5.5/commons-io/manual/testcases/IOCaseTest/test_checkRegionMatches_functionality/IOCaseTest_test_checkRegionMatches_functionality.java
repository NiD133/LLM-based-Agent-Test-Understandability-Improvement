package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class IOCaseTest_test_checkRegionMatches_functionality {

    @Test
    void test_checkRegionMatches_functionality() {
        // Matches are evaluated from the beginning of the source string.
        assertTrue(IOCase.SENSITIVE.checkRegionMatches("ABC", 0, ""));
        assertTrue(IOCase.SENSITIVE.checkRegionMatches("ABC", 0, "A"));
        assertTrue(IOCase.SENSITIVE.checkRegionMatches("ABC", 0, "AB"));
        assertTrue(IOCase.SENSITIVE.checkRegionMatches("ABC", 0, "ABC"));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches("ABC", 0, "BC"));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches("ABC", 0, "C"));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches("ABC", 0, "ABCD"));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches("", 0, "ABC"));
        assertTrue(IOCase.SENSITIVE.checkRegionMatches("", 0, ""));

        // The same searches are repeated after shifting the source start index.
        assertTrue(IOCase.SENSITIVE.checkRegionMatches("ABC", 1, ""));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches("ABC", 1, "A"));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches("ABC", 1, "AB"));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches("ABC", 1, "ABC"));
        assertTrue(IOCase.SENSITIVE.checkRegionMatches("ABC", 1, "BC"));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches("ABC", 1, "C"));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches("ABC", 1, "ABCD"));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches("", 1, "ABC"));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches("", 1, ""));

        // Null source or search strings never match.
        assertFalse(IOCase.SENSITIVE.checkRegionMatches("ABC", 0, null));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches(null, 0, "ABC"));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches(null, 0, null));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches("ABC", 1, null));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches(null, 1, "ABC"));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches(null, 1, null));
    }
}

package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link IOCase#checkRegionMatches(String, int, String)} using the
 * case-sensitive {@link IOCase#SENSITIVE} constant.
 * <p>
 * {@code checkRegionMatches(str, strStartIndex, search)} returns {@code true}
 * when {@code search} matches the region of {@code str} that begins at
 * {@code strStartIndex}. With {@code SENSITIVE} the comparison is case-sensitive,
 * and any {@code null} argument always yields {@code false}.
 * </p>
 */
public class IOCaseTest_test_checkRegionMatches_functionality {

    private final IOCase ioCase = IOCase.SENSITIVE;

    @Test
    void matchesFromStart_returnsTrueForLeadingPrefixes() {
        // search is empty or a leading prefix of "ABC" starting at index 0 -> matches
        assertTrue(ioCase.checkRegionMatches("ABC", 0, ""));
        assertTrue(ioCase.checkRegionMatches("ABC", 0, "A"));
        assertTrue(ioCase.checkRegionMatches("ABC", 0, "AB"));
        assertTrue(ioCase.checkRegionMatches("ABC", 0, "ABC"));
    }

    @Test
    void matchesFromStart_returnsFalseForNonPrefixesOrTooLong() {
        // search does not align with the region of "ABC" starting at index 0
        assertFalse(ioCase.checkRegionMatches("ABC", 0, "BC"));
        assertFalse(ioCase.checkRegionMatches("ABC", 0, "C"));
        // search is longer than the remaining region
        assertFalse(ioCase.checkRegionMatches("ABC", 0, "ABCD"));
    }

    @Test
    void matchesFromIndexOne_returnsTrueOnlyForRegionStartingThere() {
        // empty search always matches; "BC" is the region of "ABC" from index 1
        assertTrue(ioCase.checkRegionMatches("ABC", 1, ""));
        assertTrue(ioCase.checkRegionMatches("ABC", 1, "BC"));
    }

    @Test
    void matchesFromIndexOne_returnsFalseWhenRegionDiffers() {
        assertFalse(ioCase.checkRegionMatches("ABC", 1, "A"));
        assertFalse(ioCase.checkRegionMatches("ABC", 1, "AB"));
        assertFalse(ioCase.checkRegionMatches("ABC", 1, "ABC"));
        assertFalse(ioCase.checkRegionMatches("ABC", 1, "C"));
        // search is longer than the remaining region
        assertFalse(ioCase.checkRegionMatches("ABC", 1, "ABCD"));
    }

    @Test
    void matchesAgainstEmptyString() {
        // an empty string only matches an empty search at index 0
        assertFalse(ioCase.checkRegionMatches("", 0, "ABC"));
        assertTrue(ioCase.checkRegionMatches("", 0, ""));
        // index 1 is out of range for an empty string -> never matches
        assertFalse(ioCase.checkRegionMatches("", 1, "ABC"));
        assertFalse(ioCase.checkRegionMatches("", 1, ""));
    }

    @Test
    void anyNullArgument_returnsFalse() {
        assertFalse(ioCase.checkRegionMatches("ABC", 0, null));
        assertFalse(ioCase.checkRegionMatches(null, 0, "ABC"));
        assertFalse(ioCase.checkRegionMatches(null, 0, null));
        assertFalse(ioCase.checkRegionMatches("ABC", 1, null));
        assertFalse(ioCase.checkRegionMatches(null, 1, "ABC"));
        assertFalse(ioCase.checkRegionMatches(null, 1, null));
    }
}

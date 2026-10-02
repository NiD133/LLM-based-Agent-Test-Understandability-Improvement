package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link IOCase#checkRegionMatches(String, int, String)} for each case-sensitivity mode.
 * <p>
 * Every assertion checks whether the search string {@code "AB"} / {@code "Ab"} matches the
 * region of {@code "ABC"} starting at index 0. The only variable is the case-sensitivity mode,
 * so the expected result depends purely on whether casing is ignored.
 * </p>
 */
public class IOCaseTest_test_checkRegionMatches_case {

    /** True when running on Windows, whose file system is case-insensitive (drives {@code SYSTEM}). */
    private static final boolean WINDOWS = File.separatorChar == '\\';

    /** The string whose leading region is searched in every assertion. */
    private static final String TEXT = "ABC";

    /** A prefix of {@code TEXT} with identical casing. */
    private static final String SAME_CASE_PREFIX = "AB";

    /** A prefix of {@code TEXT} that differs only in casing. */
    private static final String DIFFERENT_CASE_PREFIX = "Ab";

    @Test
    void test_checkRegionMatches_case() {
        // SENSITIVE: casing matters, so only the exact-case prefix matches.
        assertTrue(IOCase.SENSITIVE.checkRegionMatches(TEXT, 0, SAME_CASE_PREFIX));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches(TEXT, 0, DIFFERENT_CASE_PREFIX));

        // INSENSITIVE: casing is ignored, so both prefixes match.
        assertTrue(IOCase.INSENSITIVE.checkRegionMatches(TEXT, 0, SAME_CASE_PREFIX));
        assertTrue(IOCase.INSENSITIVE.checkRegionMatches(TEXT, 0, DIFFERENT_CASE_PREFIX));

        // SYSTEM: the same-case prefix always matches; the different-case prefix only matches
        // when the underlying file system is case-insensitive (i.e. Windows).
        assertTrue(IOCase.SYSTEM.checkRegionMatches(TEXT, 0, SAME_CASE_PREFIX));
        assertEquals(WINDOWS, IOCase.SYSTEM.checkRegionMatches(TEXT, 0, DIFFERENT_CASE_PREFIX));
    }
}

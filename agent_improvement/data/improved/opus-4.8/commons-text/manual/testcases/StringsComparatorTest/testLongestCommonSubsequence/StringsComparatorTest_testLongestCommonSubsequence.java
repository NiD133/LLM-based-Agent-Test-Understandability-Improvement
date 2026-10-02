package org.apache.commons.text.diff;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link StringsComparator#getScript()} reports the correct
 * longest-common-subsequence (LCS) length for a variety of string pairs.
 *
 * <p>The LCS length is the number of characters that can be kept (neither
 * deleted nor inserted) when transforming the {@code left} string into the
 * {@code right} string.</p>
 */
public class StringsComparatorTest_testLongestCommonSubsequence {

    /**
     * Asserts that the LCS length computed for the given pair of strings
     * matches the expected value.
     *
     * @param left            the first string passed to the comparator.
     * @param right           the second string passed to the comparator.
     * @param expectedLcsLength the expected longest-common-subsequence length.
     */
    private static void assertLcsLength(final String left, final String right, final int expectedLcsLength) {
        final StringsComparator comparator = new StringsComparator(left, right);
        assertEquals(expectedLcsLength, comparator.getScript().getLCSLength(),
                () -> "LCS length of \"" + left + "\" and \"" + right + "\"");
    }

    @Test
    void testLongestCommonSubsequence() {
        // Common letters: "o", "t", "e"  -> "ote".
        assertLcsLength("bottle", "noodle", 3);

        // "empty bottle" contains the subsequence "bottle"... shares 7 characters.
        assertLcsLength("nematode knowledge", "empty bottle", 7);

        // Two empty strings share nothing.
        assertLcsLength("", "", 0);

        // "aa" and "C" have no characters in common.
        assertLcsLength("aa", "C", 0);

        // "prefix" is wholly contained in "prefixed string".
        assertLcsLength("prefixed string", "prefix", 6);

        // Classic Myers example: LCS of "ABCABBA" and "CBABAC" is 4 (e.g. "BABA").
        assertLcsLength("ABCABBA", "CBABAC", 4);

        // "glop glop" appears as a subsequence within "pas glop pas glop".
        assertLcsLength("glop glop", "pas glop pas glop", 9);

        // "coq" and "ane" have no characters in common.
        assertLcsLength("coq", "ane", 0);

        // "spider-man" and "klingon" share 2 characters (e.g. "in").
        assertLcsLength("spider-man", "klingon", 2);
    }
}

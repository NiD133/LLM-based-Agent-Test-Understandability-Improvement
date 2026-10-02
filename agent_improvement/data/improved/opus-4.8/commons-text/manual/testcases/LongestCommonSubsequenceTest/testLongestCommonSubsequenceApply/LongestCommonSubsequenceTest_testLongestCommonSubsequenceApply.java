package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Verifies {@link LongestCommonSubsequence#apply(CharSequence, CharSequence)}, which
 * returns the length of the longest common subsequence (LCS) shared by two strings.
 *
 * <p>A subsequence keeps character order but does not require the characters to be
 * adjacent, so the LCS length is a similarity score: {@code 0} means nothing in common,
 * while a score equal to the string length means the strings are identical.</p>
 */
public class LongestCommonSubsequenceTest_testLongestCommonSubsequenceApply {

    /** Algorithm under test; stateless, so a single shared instance is enough. */
    private static LongestCommonSubsequence lcs;

    @BeforeAll
    public static void setup() {
        lcs = new LongestCommonSubsequence();
    }

    /**
     * Asserts that the LCS length of {@code left} and {@code right} equals
     * {@code expectedLength}, making each scenario read as a single intent-revealing line.
     */
    private static void assertLcsLength(final int expectedLength, final String left, final String right) {
        assertEquals(expectedLength, lcs.apply(left, right));
    }

    @Test
    void testLongestCommonSubsequenceApply() {
        // Empty inputs: nothing can be shared, so the score is always 0.
        assertLcsLength(0, "", "");
        assertLcsLength(0, "left", "");
        assertLcsLength(0, "", "right");

        // No characters in common (in order) yields 0; a partial overlap yields its length.
        assertLcsLength(0, "fly", "ant");
        assertLcsLength(1, "elephant", "hippo");
        assertLcsLength(1, "left", "right");
        assertLcsLength(3, "frog", "fog");
        assertLcsLength(4, "leettteft", "ritttght");

        // Real-world style strings with punctuation/spacing differences.
        assertLcsLength(8, "ABC Corporation", "ABC Corp");
        assertLcsLength(11, "PENNSYLVANIA", "PENNCISYLVNIA");
        assertLcsLength(20, "D N H Enterprises Inc", "D & H Enterprises, Inc.");
        assertLcsLength(24, "My Gym Children's Fitness Center", "My Gym. Childrens Fitness");

        // Identical strings: the LCS spans the whole string ("the same string" has length 15).
        assertLcsLength(15, "the same string", "the same string");
    }
}

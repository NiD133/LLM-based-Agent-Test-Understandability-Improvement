package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Verifies {@link LongestCommonSubsequence#longestCommonSubstringLengthArray(CharSequence, CharSequence)},
 * the dynamic-programming (DP) table builder used by the LCS algorithm.
 *
 * <p>The DP table has {@code left.length() + 1} rows and {@code right.length() + 1} columns. Cell
 * {@code [i][j]} holds the length of the longest common subsequence of the first {@code i} characters
 * of {@code left} and the first {@code j} characters of {@code right}. Row 0 and column 0 act as the
 * empty-prefix base cases and are therefore all zeros.</p>
 */
public class LongestCommonSubsequenceTest_testLongestCommonSubstringLengthArray {

    private static LongestCommonSubsequence longestCommonSubsequence;

    @BeforeAll
    public static void setUp() {
        longestCommonSubsequence = new LongestCommonSubsequence();
    }

    @Test
    void buildsExpectedDynamicProgrammingTableForAbAndAbc() {
        final CharSequence left = "ab";
        final CharSequence right = "abc";

        // Rows correspond to prefixes of "ab" ("", "a", "ab"),
        // columns to prefixes of "abc" ("", "a", "ab", "abc").
        final int[][] expectedDpTable = {
            //  ""  a   b   c   <- prefixes of "abc"
            {    0,  0,  0,  0 }, // prefix ""
            {    0,  1,  1,  1 }, // prefix "a"
            {    0,  1,  2,  2 }, // prefix "ab"
        };

        final int[][] actualDpTable = longestCommonSubsequence.longestCommonSubstringLengthArray(left, right);

        assertArrayEquals(expectedDpTable, actualDpTable);
    }
}

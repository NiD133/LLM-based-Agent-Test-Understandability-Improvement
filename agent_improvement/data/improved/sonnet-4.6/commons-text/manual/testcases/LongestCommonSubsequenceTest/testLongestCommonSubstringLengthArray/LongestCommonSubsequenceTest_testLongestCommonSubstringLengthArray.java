package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LongestCommonSubsequenceTest_testLongestCommonSubstringLengthArray {

    private static LongestCommonSubsequence subject;

    @BeforeAll
    public static void setup() {
        subject = new LongestCommonSubsequence();
    }

    @Test
    void testLongestCommonSubstringLengthArray() {
        // The DP table has (left.length+1) rows and (right.length+1) columns.
        // Row 0 and column 0 are the base-case zeros; subsequent cells encode LCS lengths.
        // For left="ab" and right="abc":
        //   row 0 (base): [0, 0, 0, 0]
        //   row 1 ('a'):  [0, 1, 1, 1]   -- 'a' matches right[0]
        //   row 2 ('b'):  [0, 1, 2, 2]   -- 'b' matches right[1]
        int[][] expectedLcsTable = {
            { 0, 0, 0, 0 },
            { 0, 1, 1, 1 },
            { 0, 1, 2, 2 }
        };

        assertArrayEquals(expectedLcsTable, subject.longestCommonSubstringLengthArray("ab", "abc"));
    }
}

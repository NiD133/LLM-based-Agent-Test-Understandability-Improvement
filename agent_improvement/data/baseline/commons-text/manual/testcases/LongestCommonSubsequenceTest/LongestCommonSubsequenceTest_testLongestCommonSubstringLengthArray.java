package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        assertArrayEquals(new int[][] { { 0, 0, 0, 0 }, { 0, 1, 1, 1 }, { 0, 1, 2, 2 } }, subject.longestCommonSubstringLengthArray("ab", "abc"));
    }
}

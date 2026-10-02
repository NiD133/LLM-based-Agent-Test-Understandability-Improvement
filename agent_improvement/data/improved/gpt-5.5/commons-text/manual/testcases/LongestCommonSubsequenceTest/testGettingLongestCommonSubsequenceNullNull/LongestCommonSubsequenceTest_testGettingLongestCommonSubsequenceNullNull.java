package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LongestCommonSubsequenceTest_testGettingLongestCommonSubsequenceNullNull {

    private static LongestCommonSubsequence longestCommonSubsequence;

    @BeforeAll
    public static void setUp() {
        longestCommonSubsequence = new LongestCommonSubsequence();
    }

    @Test
    void longestCommonSubsequenceThrowsWhenBothInputsAreNull() {
        assertThrows(
                IllegalArgumentException.class,
                () -> longestCommonSubsequence.longestCommonSubsequence(null, null));
    }
}

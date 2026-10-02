package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link LongestCommonSubsequence#longestCommonSubsequence(CharSequence, CharSequence)}
 * rejects a {@code null} argument.
 */
public class LongestCommonSubsequenceTest_testGettingLongestCommonSubsequenceStringNull {

    /** Shared algorithm instance exercised by the test. */
    private static LongestCommonSubsequence longestCommonSubsequence;

    @BeforeAll
    public static void createAlgorithm() {
        longestCommonSubsequence = new LongestCommonSubsequence();
    }

    @Test
    void longestCommonSubsequenceThrowsWhenRightInputIsNull() {
        final CharSequence nonNullLeft = " ";
        final CharSequence nullRight = null;

        assertThrows(IllegalArgumentException.class,
                () -> longestCommonSubsequence.longestCommonSubsequence(nonNullLeft, nullRight));
    }
}

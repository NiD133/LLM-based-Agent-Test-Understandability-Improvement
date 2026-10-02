package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link LongestCommonSubsequence#longestCommonSubsequence(CharSequence, CharSequence)}
 * rejects a {@code null} argument by throwing an {@link IllegalArgumentException}.
 */
public class LongestCommonSubsequenceTest_testGettingLongestCommonSubsequenceStringNull {

    /** Shared instance under test; it holds no state, so a single instance is reused across tests. */
    private static LongestCommonSubsequence longestCommonSubsequence;

    @BeforeAll
    public static void setUp() {
        longestCommonSubsequence = new LongestCommonSubsequence();
    }

    @Test
    void longestCommonSubsequenceRejectsNullRightArgument() {
        final CharSequence nonNullLeft = " ";
        final CharSequence nullRight = null;

        assertThrows(IllegalArgumentException.class,
                () -> longestCommonSubsequence.longestCommonSubsequence(nonNullLeft, nullRight));
    }
}

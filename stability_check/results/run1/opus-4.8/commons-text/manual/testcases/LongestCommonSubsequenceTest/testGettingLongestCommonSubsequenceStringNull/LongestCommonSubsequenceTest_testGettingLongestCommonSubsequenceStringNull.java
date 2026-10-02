package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link LongestCommonSubsequence#longestCommonSubsequence(CharSequence, CharSequence)}
 * rejects a {@code null} argument.
 */
public class LongestCommonSubsequenceTest_testGettingLongestCommonSubsequenceStringNull {

    /**
     * A {@code null} second argument must trigger an {@link IllegalArgumentException},
     * regardless of the first argument.
     */
    @Test
    void longestCommonSubsequenceRejectsNullRightArgument() {
        final LongestCommonSubsequence lcs = new LongestCommonSubsequence();

        assertThrows(IllegalArgumentException.class,
                () -> lcs.longestCommonSubsequence(" ", null));
    }
}

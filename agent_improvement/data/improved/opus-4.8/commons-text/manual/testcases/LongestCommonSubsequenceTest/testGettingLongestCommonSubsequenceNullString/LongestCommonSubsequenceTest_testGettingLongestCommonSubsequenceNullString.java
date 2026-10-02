package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies the null-input contract of
 * {@link LongestCommonSubsequence#longestCommonSubsequence(CharSequence, CharSequence)}.
 */
public class LongestCommonSubsequenceTest_testGettingLongestCommonSubsequenceNullString {

    private final LongestCommonSubsequence longestCommonSubsequence = new LongestCommonSubsequence();

    @Test
    void rejectsNullLeftInputWithIllegalArgumentException() {
        final CharSequence nullLeftInput = null;
        final CharSequence nonNullRightInput = "right";

        assertThrows(IllegalArgumentException.class,
                () -> longestCommonSubsequence.longestCommonSubsequence(nullLeftInput, nonNullRightInput));
    }
}

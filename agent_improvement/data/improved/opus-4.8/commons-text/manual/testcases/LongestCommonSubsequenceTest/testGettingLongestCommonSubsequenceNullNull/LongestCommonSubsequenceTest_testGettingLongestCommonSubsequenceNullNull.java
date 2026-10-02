package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies the null-handling contract of
 * {@link LongestCommonSubsequence#longestCommonSubsequence(CharSequence, CharSequence)}.
 */
public class LongestCommonSubsequenceTest_testGettingLongestCommonSubsequenceNullNull {

    /** Instance under test. */
    private final LongestCommonSubsequence longestCommonSubsequence = new LongestCommonSubsequence();

    @Test
    void rejectsNullForBothInputs() {
        // When both the left and right sequences are null, the method must reject them
        // rather than attempting a comparison.
        assertThrows(IllegalArgumentException.class,
            () -> longestCommonSubsequence.longestCommonSubsequence(null, null));
    }
}

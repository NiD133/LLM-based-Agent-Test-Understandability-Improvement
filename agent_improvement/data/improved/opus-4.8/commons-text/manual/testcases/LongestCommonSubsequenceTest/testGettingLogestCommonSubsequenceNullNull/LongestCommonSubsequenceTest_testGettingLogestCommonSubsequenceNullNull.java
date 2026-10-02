package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies the null-handling contract of the deprecated
 * {@link LongestCommonSubsequence#logestCommonSubsequence(CharSequence, CharSequence)} method.
 */
public class LongestCommonSubsequenceTest_testGettingLogestCommonSubsequenceNullNull {

    /**
     * When both the left and right sequences are {@code null}, the method must reject the
     * input by throwing an {@link IllegalArgumentException} rather than returning a result.
     */
    @Test
    @SuppressWarnings("deprecation")
    void logestCommonSubsequenceRejectsTwoNullInputs() {
        final LongestCommonSubsequence lcs = new LongestCommonSubsequence();

        assertThrows(IllegalArgumentException.class,
                () -> lcs.logestCommonSubsequence(null, null));
    }
}

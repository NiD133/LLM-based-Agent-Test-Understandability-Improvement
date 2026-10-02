package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link LongestCommonSubsequence#apply(CharSequence, CharSequence)}
 * rejects {@code null} inputs.
 *
 * <p>The {@code apply} method computes the length of the longest common subsequence
 * of two character sequences. Its contract states that an {@link IllegalArgumentException}
 * is thrown when either input is {@code null}.</p>
 */
public class LongestCommonSubsequenceTest_testGettingLongestCommonSubsequenceApplyNullNull {

    /** The algorithm instance under test. */
    private final LongestCommonSubsequence lcs = new LongestCommonSubsequence();

    @Test
    void applyThrowsIllegalArgumentExceptionWhenBothInputsAreNull() {
        assertThrows(IllegalArgumentException.class, () -> lcs.apply(null, null));
    }
}

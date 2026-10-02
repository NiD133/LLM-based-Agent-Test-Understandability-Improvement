package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that the (deprecated) {@code logestCommonSubsequence} method rejects a {@code null} argument.
 */
public class LongestCommonSubsequenceTest_testGettingLogestCommonSubsequenceStringNull {

    /** The algorithm instance under test. */
    private final LongestCommonSubsequence longestCommonSubsequence = new LongestCommonSubsequence();

    @Test
    @SuppressWarnings("deprecation")
    void throwsIllegalArgumentExceptionWhenSecondArgumentIsNull() {
        final CharSequence nonNullInput = " ";
        final CharSequence nullInput = null;

        assertThrows(IllegalArgumentException.class,
                () -> longestCommonSubsequence.logestCommonSubsequence(nonNullInput, nullInput));
    }
}

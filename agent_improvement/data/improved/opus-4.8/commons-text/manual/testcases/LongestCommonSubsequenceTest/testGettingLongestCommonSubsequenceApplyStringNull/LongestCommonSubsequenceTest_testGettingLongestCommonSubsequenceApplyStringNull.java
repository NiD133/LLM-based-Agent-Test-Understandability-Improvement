package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link LongestCommonSubsequence#apply(CharSequence, CharSequence)}
 * rejects a {@code null} argument by throwing an {@link IllegalArgumentException},
 * as documented in the method's contract.
 */
public class LongestCommonSubsequenceTest_testGettingLongestCommonSubsequenceApplyStringNull {

    @Test
    void applyShouldThrowWhenRightInputIsNull() {
        final LongestCommonSubsequence longestCommonSubsequence = new LongestCommonSubsequence();
        final CharSequence nonNullLeftInput = " ";
        final CharSequence nullRightInput = null;

        assertThrows(IllegalArgumentException.class,
                () -> longestCommonSubsequence.apply(nonNullLeftInput, nullRightInput));
    }
}

package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link LongestCommonSubsequence#apply(CharSequence, CharSequence)}
 * rejects a {@code null} input rather than computing a score.
 */
public class LongestCommonSubsequenceTest_testGettingLongestCommonSubsequenceApplyNullString {

    /** The algorithm instance under test. */
    private final LongestCommonSubsequence lcs = new LongestCommonSubsequence();

    @Test
    void applyWithNullLeftInputThrowsIllegalArgumentException() {
        // The left sequence is null while the right sequence is valid.
        final CharSequence nullLeft = null;
        final CharSequence validRight = "right";

        // apply(..) must reject the null input by throwing IllegalArgumentException.
        assertThrows(IllegalArgumentException.class, () -> lcs.apply(nullLeft, validRight));
    }
}

package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Verifies the null-handling contract of the deprecated
 * {@link LongestCommonSubsequence#logestCommonSubsequence(CharSequence, CharSequence)} method.
 */
public class LongestCommonSubsequenceTest_testGettingLogestCommonSubsequenceNullString {

    /** The algorithm instance shared by the tests in this class. */
    private static LongestCommonSubsequence longestCommonSubsequence;

    @BeforeAll
    public static void createSubjectUnderTest() {
        longestCommonSubsequence = new LongestCommonSubsequence();
    }

    /**
     * A null {@code left} input must be rejected with an {@link IllegalArgumentException},
     * regardless of the (non-null) {@code right} input.
     */
    @Test
    @SuppressWarnings("deprecation")
    void nullLeftInputThrowsIllegalArgumentException() {
        final CharSequence nullLeft = null;
        final CharSequence nonNullRight = "right";

        assertThrows(IllegalArgumentException.class,
                () -> longestCommonSubsequence.logestCommonSubsequence(nullLeft, nonNullRight));
    }
}

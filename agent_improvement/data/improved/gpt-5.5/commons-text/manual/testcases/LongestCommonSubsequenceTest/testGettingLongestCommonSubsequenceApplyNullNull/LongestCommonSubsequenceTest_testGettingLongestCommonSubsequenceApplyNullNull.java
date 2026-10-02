package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LongestCommonSubsequenceTest_testGettingLongestCommonSubsequenceApplyNullNull {

    private static LongestCommonSubsequence subject;

    @BeforeAll
    public static void setUp() {
        subject = new LongestCommonSubsequence();
    }

    @Test
    void applyThrowsIllegalArgumentExceptionWhenBothInputsAreNull() {
        final CharSequence left = null;
        final CharSequence right = null;

        assertThrows(IllegalArgumentException.class, () -> subject.apply(left, right));
    }
}

package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LongestCommonSubsequenceTest_testGettingLongestCommonSubsequenceStringNull {

    private static LongestCommonSubsequence subject;

    @BeforeAll
    public static void setUp() {
        subject = new LongestCommonSubsequence();
    }

    @Test
    void throwsIllegalArgumentExceptionWhenRightInputIsNull() {
        assertThrows(IllegalArgumentException.class, () -> subject.longestCommonSubsequence(" ", null));
    }
}

package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LongestCommonSubsequenceTest_testGettingLongestCommonSubsequenceApplyStringNull {

    private static final String LEFT_INPUT = " ";
    private static final String NULL_RIGHT_INPUT = null;

    private static LongestCommonSubsequence subject;

    @BeforeAll
    public static void setUpSubject() {
        subject = new LongestCommonSubsequence();
    }

    @Test
    void applyThrowsIllegalArgumentExceptionWhenRightInputIsNull() {
        assertThrows(IllegalArgumentException.class, () -> subject.apply(LEFT_INPUT, NULL_RIGHT_INPUT));
    }
}

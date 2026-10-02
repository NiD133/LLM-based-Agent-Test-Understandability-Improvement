package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LongestCommonSubsequenceTest_testGettingLogestCommonSubsequenceStringNull {

    private static final CharSequence LEFT_VALUE = " ";
    private static final CharSequence NULL_RIGHT_VALUE = null;

    private static LongestCommonSubsequence subject;

    @BeforeAll
    public static void setUpSubject() {
        subject = new LongestCommonSubsequence();
    }

    @Test
    @SuppressWarnings("deprecation")
    void throwsIllegalArgumentExceptionWhenRightInputIsNull() {
        assertThrows(
                IllegalArgumentException.class,
                () -> subject.logestCommonSubsequence(LEFT_VALUE, NULL_RIGHT_VALUE));
    }
}

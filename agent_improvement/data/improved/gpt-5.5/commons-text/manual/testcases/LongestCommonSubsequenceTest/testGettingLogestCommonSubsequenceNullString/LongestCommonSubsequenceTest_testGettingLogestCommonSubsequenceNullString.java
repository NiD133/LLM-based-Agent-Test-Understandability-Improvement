package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LongestCommonSubsequenceTest_testGettingLogestCommonSubsequenceNullString {

    private static final String RIGHT_SEQUENCE = "right";

    private static LongestCommonSubsequence subject;

    @BeforeAll
    public static void setUp() {
        subject = new LongestCommonSubsequence();
    }

    @Test
    @SuppressWarnings("deprecation")
    void throwsIllegalArgumentExceptionForNullLeftSequenceUsingDeprecatedTypoMethod() {
        assertThrows(
                IllegalArgumentException.class,
                () -> subject.logestCommonSubsequence(null, RIGHT_SEQUENCE));
    }
}

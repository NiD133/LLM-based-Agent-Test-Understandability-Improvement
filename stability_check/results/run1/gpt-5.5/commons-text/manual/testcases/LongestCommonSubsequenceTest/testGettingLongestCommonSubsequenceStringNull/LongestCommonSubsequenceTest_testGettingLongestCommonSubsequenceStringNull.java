package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LongestCommonSubsequenceTest_testGettingLongestCommonSubsequenceStringNull {

    private static final String SPACE_ONLY_SEQUENCE = " ";
    private static final String NULL_SEQUENCE = null;

    private static LongestCommonSubsequence subject;

    @BeforeAll
    public static void setup() {
        subject = new LongestCommonSubsequence();
    }

    @Test
    void testGettingLongestCommonSubsequenceStringNull() {
        assertThrows(IllegalArgumentException.class,
                () -> subject.longestCommonSubsequence(SPACE_ONLY_SEQUENCE, NULL_SEQUENCE));
    }
}

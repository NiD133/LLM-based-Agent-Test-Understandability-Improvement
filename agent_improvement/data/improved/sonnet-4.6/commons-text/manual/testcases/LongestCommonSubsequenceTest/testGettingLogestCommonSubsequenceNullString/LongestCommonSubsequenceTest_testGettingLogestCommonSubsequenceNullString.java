package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LongestCommonSubsequenceTest_testGettingLogestCommonSubsequenceNullString {

    private static LongestCommonSubsequence subject;

    @BeforeAll
    public static void setup() {
        subject = new LongestCommonSubsequence();
    }

    /**
     * Verifies that the deprecated logestCommonSubsequence method rejects a null left argument
     * by throwing IllegalArgumentException, consistent with the non-deprecated variant's contract.
     *
     * @SuppressWarnings is required because logestCommonSubsequence is deprecated (typo in name);
     * the replacement is longestCommonSubsequence.
     */
    @Test
    @SuppressWarnings("deprecation")
    void testGettingLogestCommonSubsequenceNullString() {
        final String nullLeft = null;
        final String right = "right";

        assertThrows(IllegalArgumentException.class,
                () -> subject.logestCommonSubsequence(nullLeft, right));
    }
}

package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LongestCommonSubsequenceTest_testGettingLongestCommonSubsequenceApplyNullNull {

    private static LongestCommonSubsequence subject;

    @BeforeAll
    public static void setup() {
        subject = new LongestCommonSubsequence();
    }

    /**
     * Verifies that apply() rejects null inputs for both arguments by throwing
     * IllegalArgumentException, as documented in the method contract.
     */
    @Test
    @DisplayName("apply(null, null) throws IllegalArgumentException")
    void testGettingLongestCommonSubsequenceApplyNullNull() {
        assertThrows(IllegalArgumentException.class, () -> subject.apply(null, null));
    }
}

package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LongestCommonSubsequenceTest_testGettingLongestCommonSubsequenceStringNull {

    private static LongestCommonSubsequence subject;

    @BeforeAll
    public static void setup() {
        subject = new LongestCommonSubsequence();
    }

    /**
     * Verifies that passing null as the second (right) argument to
     * longestCommonSubsequence throws an IllegalArgumentException,
     * enforcing the documented contract that neither input may be null.
     */
    @Test
    @DisplayName("longestCommonSubsequence throws IllegalArgumentException when right argument is null")
    void testGettingLongestCommonSubsequenceStringNull() {
        String validLeft = " ";
        String nullRight = null;

        assertThrows(IllegalArgumentException.class,
                () -> subject.longestCommonSubsequence(validLeft, nullRight));
    }
}

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

    @Test
    @DisplayName("longestCommonSubsequence throws IllegalArgumentException when the right argument is null")
    void testGettingLongestCommonSubsequenceStringNull() {
        // Passing null as the second argument must be rejected with an IllegalArgumentException,
        // because the contract of longestCommonSubsequence requires both inputs to be non-null.
        assertThrows(
                IllegalArgumentException.class,
                () -> subject.longestCommonSubsequence(" ", null));
    }
}

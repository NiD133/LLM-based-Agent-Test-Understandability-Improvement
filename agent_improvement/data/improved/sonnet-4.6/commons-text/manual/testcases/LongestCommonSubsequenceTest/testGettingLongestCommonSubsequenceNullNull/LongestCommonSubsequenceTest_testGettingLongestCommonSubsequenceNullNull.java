package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LongestCommonSubsequenceTest_testGettingLongestCommonSubsequenceNullNull {

    private static LongestCommonSubsequence subject;

    @BeforeAll
    public static void setup() {
        subject = new LongestCommonSubsequence();
    }

    // longestCommonSubsequence requires non-null inputs; passing null for both must throw.
    @Test
    void testGettingLongestCommonSubsequenceNullNull() {
        assertThrows(IllegalArgumentException.class, () -> subject.longestCommonSubsequence(null, null));
    }
}

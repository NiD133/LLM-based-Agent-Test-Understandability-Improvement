package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LongestCommonSubsequenceTest_testGettingLongestCommonSubsequenceApplyNullString {

    private static LongestCommonSubsequence subject;

    @BeforeAll
    public static void setup() {
        subject = new LongestCommonSubsequence();
    }

    @Test
    void testGettingLongestCommonSubsequenceApplyNullString() {
        assertThrows(IllegalArgumentException.class, () -> subject.apply(null, "right"));
    }
}

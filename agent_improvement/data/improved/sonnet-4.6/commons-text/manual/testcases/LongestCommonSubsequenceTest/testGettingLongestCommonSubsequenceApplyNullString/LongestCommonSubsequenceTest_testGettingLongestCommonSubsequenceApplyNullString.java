package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LongestCommonSubsequenceTest_testGettingLongestCommonSubsequenceApplyNullString {

    private static LongestCommonSubsequence subject;

    @BeforeAll
    public static void setup() {
        subject = new LongestCommonSubsequence();
    }

    // apply() must reject a null left argument by throwing IllegalArgumentException
    @Test
    void testGettingLongestCommonSubsequenceApplyNullString() {
        assertThrows(IllegalArgumentException.class, () -> subject.apply(null, "right"));
    }
}

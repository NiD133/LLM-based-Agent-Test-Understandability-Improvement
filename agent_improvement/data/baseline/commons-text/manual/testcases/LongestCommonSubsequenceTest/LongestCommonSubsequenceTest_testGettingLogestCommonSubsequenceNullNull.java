package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LongestCommonSubsequenceTest_testGettingLogestCommonSubsequenceNullNull {

    private static LongestCommonSubsequence subject;

    @BeforeAll
    public static void setup() {
        subject = new LongestCommonSubsequence();
    }

    @Test
    @SuppressWarnings("deprecation")
    void testGettingLogestCommonSubsequenceNullNull() {
        assertThrows(IllegalArgumentException.class, () -> subject.logestCommonSubsequence(null, null));
    }
}

package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LongestCommonSubsequenceTest_testGettingLogestCommonSubsequenceNullNull {

    private static LongestCommonSubsequence subject;

    @BeforeAll
    public static void setup() {
        subject = new LongestCommonSubsequence();
    }

    /**
     * Verifies that the deprecated {@code logestCommonSubsequence} method (note the typo in the
     * original name — it delegates to {@code longestCommonSubsequence}) rejects {@code null} inputs
     * by throwing {@link IllegalArgumentException} when both arguments are {@code null}.
     */
    @Test
    // logestCommonSubsequence is deprecated in favour of longestCommonSubsequence; suppress the
    // deprecation warning so the test can exercise the old entry-point directly.
    @SuppressWarnings("deprecation")
    void testGettingLogestCommonSubsequenceNullNull() {
        assertThrows(IllegalArgumentException.class, () -> subject.logestCommonSubsequence(null, null));
    }
}

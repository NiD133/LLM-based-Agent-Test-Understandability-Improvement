package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link LongestCommonSubsequence#logestCommonSubsequence} rejects null input.
 */
public class LongestCommonSubsequenceTest_testGettingLogestCommonSubsequenceStringNull {

    private static LongestCommonSubsequence lcs;

    @BeforeAll
    public static void setup() {
        lcs = new LongestCommonSubsequence();
    }

    /**
     * Passing null as the second argument must throw {@link IllegalArgumentException},
     * because neither input to the LCS computation may be null.
     */
    @Test
    @SuppressWarnings("deprecation")
    void testLogestCommonSubsequenceThrowsWhenSecondArgumentIsNull() {
        assertThrows(
            IllegalArgumentException.class,
            () -> lcs.logestCommonSubsequence(" ", null),
            "logestCommonSubsequence should throw IllegalArgumentException when the right argument is null"
        );
    }
}

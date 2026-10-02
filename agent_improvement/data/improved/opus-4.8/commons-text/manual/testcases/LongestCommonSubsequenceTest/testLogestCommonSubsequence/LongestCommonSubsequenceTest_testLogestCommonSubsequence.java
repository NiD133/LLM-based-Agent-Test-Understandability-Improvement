package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Tests the (deprecated, typo-named) {@link LongestCommonSubsequence#logestCommonSubsequence}
 * method, which returns the actual longest common subsequence (LCS) shared by two strings.
 */
public class LongestCommonSubsequenceTest_testLogestCommonSubsequence {

    private static LongestCommonSubsequence subject;

    @BeforeAll
    public static void setup() {
        subject = new LongestCommonSubsequence();
    }

    /**
     * Asserts that the LCS of {@code left} and {@code right} equals the {@code expected} string.
     */
    private static void assertLcs(final String expected, final String left, final String right) {
        assertEquals(expected, subject.logestCommonSubsequence(left, right));
    }

    @Test
    void testLogestCommonSubsequence() {
        // When either side is empty, there is nothing in common.
        assertLcs("", "", "");
        assertLcs("", "left", "");
        assertLcs("", "", "right");

        // No shared characters -> empty subsequence.
        assertLcs("", "l", "a");
        assertLcs("", "left", "a");
        assertLcs("", "fly", "ant");

        // Some characters are shared, in order.
        assertLcs("fog", "frog", "fog");
        assertLcs("h", "elephant", "hippo");
        assertLcs("t", "left", "right");
        assertLcs("tttt", "leettteft", "ritttght");

        // Realistic, punctuation-heavy strings (e.g. company-name matching).
        assertLcs("ABC Corp", "ABC Corporation", "ABC Corp");
        assertLcs("D  H Enterprises Inc", "D N H Enterprises Inc", "D & H Enterprises, Inc.");
        assertLcs("My Gym Childrens Fitness", "My Gym Children's Fitness Center", "My Gym. Childrens Fitness");
        assertLcs("PENNSYLVNIA", "PENNSYLVANIA", "PENNCISYLVNIA");

        // Identical inputs -> the whole string is common.
        assertLcs("the same string", "the same string", "the same string");
    }
}

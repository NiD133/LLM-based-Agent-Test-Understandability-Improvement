package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LongestCommonSubsequenceTest_testLongestCommonSubsequence {

    private static LongestCommonSubsequence subject;

    @BeforeAll
    public static void setup() {
        subject = new LongestCommonSubsequence();
    }

    @Test
    void testLongestCommonSubsequence() {
        assertLongestCommonSubsequence("", "", "");
        assertLongestCommonSubsequence("", "left", "");
        assertLongestCommonSubsequence("", "", "right");

        assertLongestCommonSubsequence("", "l", "a");
        assertLongestCommonSubsequence("", "left", "a");
        assertLongestCommonSubsequence("", "fly", "ant");

        assertLongestCommonSubsequence("fog", "frog", "fog");
        assertLongestCommonSubsequence("h", "elephant", "hippo");
        assertLongestCommonSubsequence("ABC Corp", "ABC Corporation", "ABC Corp");
        assertLongestCommonSubsequence("D  H Enterprises Inc", "D N H Enterprises Inc", "D & H Enterprises, Inc.");
        assertLongestCommonSubsequence("My Gym Childrens Fitness", "My Gym Children's Fitness Center", "My Gym. Childrens Fitness");
        assertLongestCommonSubsequence("PENNSYLVNIA", "PENNSYLVANIA", "PENNCISYLVNIA");

        assertLongestCommonSubsequence("t", "left", "right");
        assertLongestCommonSubsequence("tttt", "leettteft", "ritttght");
        assertLongestCommonSubsequence("the same string", "the same string", "the same string");
    }

    private void assertLongestCommonSubsequence(final String expected, final String left, final String right) {
        assertEquals(expected, subject.longestCommonSubsequence(left, right));
    }
}

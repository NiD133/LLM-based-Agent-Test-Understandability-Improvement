package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LongestCommonSubsequenceTest_testLogestCommonSubsequence {

    private static LongestCommonSubsequence subject;

    @BeforeAll
    public static void setup() {
        subject = new LongestCommonSubsequence();
    }

    @Test
    void testLogestCommonSubsequence() {
        assertCommonSubsequence("", "", "");
        assertCommonSubsequence("", "left", "");
        assertCommonSubsequence("", "", "right");
        assertCommonSubsequence("", "l", "a");
        assertCommonSubsequence("", "left", "a");

        assertCommonSubsequence("fog", "frog", "fog");
        assertCommonSubsequence("", "fly", "ant");
        assertCommonSubsequence("h", "elephant", "hippo");
        assertCommonSubsequence("ABC Corp", "ABC Corporation", "ABC Corp");
        assertCommonSubsequence("D  H Enterprises Inc", "D N H Enterprises Inc", "D & H Enterprises, Inc.");
        assertCommonSubsequence("My Gym Childrens Fitness", "My Gym Children's Fitness Center", "My Gym. Childrens Fitness");
        assertCommonSubsequence("PENNSYLVNIA", "PENNSYLVANIA", "PENNCISYLVNIA");

        assertCommonSubsequence("t", "left", "right");
        assertCommonSubsequence("tttt", "leettteft", "ritttght");
        assertCommonSubsequence("the same string", "the same string", "the same string");
    }

    @SuppressWarnings("deprecation")
    private static void assertCommonSubsequence(final String expected, final String left, final String right) {
        assertEquals(expected, subject.logestCommonSubsequence(left, right));
    }
}

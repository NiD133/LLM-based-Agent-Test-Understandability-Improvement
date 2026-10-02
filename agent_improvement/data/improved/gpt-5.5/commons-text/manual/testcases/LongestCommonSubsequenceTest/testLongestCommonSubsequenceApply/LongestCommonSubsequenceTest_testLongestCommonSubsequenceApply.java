package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LongestCommonSubsequenceTest_testLongestCommonSubsequenceApply {

    private static LongestCommonSubsequence subject;

    @BeforeAll
    public static void setup() {
        subject = new LongestCommonSubsequence();
    }

    @Test
    void testLongestCommonSubsequenceApply() {
        assertLongestCommonSubsequenceLength(0, "", "");
        assertLongestCommonSubsequenceLength(0, "left", "");
        assertLongestCommonSubsequenceLength(0, "", "right");

        assertLongestCommonSubsequenceLength(3, "frog", "fog");
        assertLongestCommonSubsequenceLength(0, "fly", "ant");
        assertLongestCommonSubsequenceLength(1, "elephant", "hippo");
        assertLongestCommonSubsequenceLength(8, "ABC Corporation", "ABC Corp");
        assertLongestCommonSubsequenceLength(20, "D N H Enterprises Inc", "D & H Enterprises, Inc.");
        assertLongestCommonSubsequenceLength(24, "My Gym Children's Fitness Center", "My Gym. Childrens Fitness");
        assertLongestCommonSubsequenceLength(11, "PENNSYLVANIA", "PENNCISYLVNIA");
        assertLongestCommonSubsequenceLength(1, "left", "right");
        assertLongestCommonSubsequenceLength(4, "leettteft", "ritttght");
        assertLongestCommonSubsequenceLength(15, "the same string", "the same string");
    }

    private static void assertLongestCommonSubsequenceLength(final int expectedLength, final String left,
            final String right) {
        assertEquals(expectedLength, subject.apply(left, right));
    }
}

package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        assertEquals("", subject.longestCommonSubsequence("", ""));
        assertEquals("", subject.longestCommonSubsequence("left", ""));
        assertEquals("", subject.longestCommonSubsequence("", "right"));
        assertEquals("", subject.longestCommonSubsequence("l", "a"));
        assertEquals("", subject.longestCommonSubsequence("left", "a"));
        assertEquals("fog", subject.longestCommonSubsequence("frog", "fog"));
        assertEquals("", subject.longestCommonSubsequence("fly", "ant"));
        assertEquals("h", subject.longestCommonSubsequence("elephant", "hippo"));
        assertEquals("ABC Corp", subject.longestCommonSubsequence("ABC Corporation", "ABC Corp"));
        assertEquals("D  H Enterprises Inc", subject.longestCommonSubsequence("D N H Enterprises Inc", "D & H Enterprises, Inc."));
        assertEquals("My Gym Childrens Fitness", subject.longestCommonSubsequence("My Gym Children's Fitness Center", "My Gym. Childrens Fitness"));
        assertEquals("PENNSYLVNIA", subject.longestCommonSubsequence("PENNSYLVANIA", "PENNCISYLVNIA"));
        assertEquals("t", subject.longestCommonSubsequence("left", "right"));
        assertEquals("tttt", subject.longestCommonSubsequence("leettteft", "ritttght"));
        assertEquals("the same string", subject.longestCommonSubsequence("the same string", "the same string"));
    }
}

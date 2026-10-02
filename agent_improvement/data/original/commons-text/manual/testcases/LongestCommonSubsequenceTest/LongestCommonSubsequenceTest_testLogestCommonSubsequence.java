package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        assertEquals("", subject.logestCommonSubsequence("", ""));
        assertEquals("", subject.logestCommonSubsequence("left", ""));
        assertEquals("", subject.logestCommonSubsequence("", "right"));
        assertEquals("", subject.logestCommonSubsequence("l", "a"));
        assertEquals("", subject.logestCommonSubsequence("left", "a"));
        assertEquals("fog", subject.logestCommonSubsequence("frog", "fog"));
        assertEquals("", subject.logestCommonSubsequence("fly", "ant"));
        assertEquals("h", subject.logestCommonSubsequence("elephant", "hippo"));
        assertEquals("ABC Corp", subject.logestCommonSubsequence("ABC Corporation", "ABC Corp"));
        assertEquals("D  H Enterprises Inc", subject.logestCommonSubsequence("D N H Enterprises Inc", "D & H Enterprises, Inc."));
        assertEquals("My Gym Childrens Fitness", subject.logestCommonSubsequence("My Gym Children's Fitness Center", "My Gym. Childrens Fitness"));
        assertEquals("PENNSYLVNIA", subject.logestCommonSubsequence("PENNSYLVANIA", "PENNCISYLVNIA"));
        assertEquals("t", subject.logestCommonSubsequence("left", "right"));
        assertEquals("tttt", subject.logestCommonSubsequence("leettteft", "ritttght"));
        assertEquals("the same string", subject.logestCommonSubsequence("the same string", "the same string"));
    }
}

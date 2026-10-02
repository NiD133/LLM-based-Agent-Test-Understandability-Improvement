package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        assertEquals(0, subject.apply("", ""));
        assertEquals(0, subject.apply("left", ""));
        assertEquals(0, subject.apply("", "right"));
        assertEquals(3, subject.apply("frog", "fog"));
        assertEquals(0, subject.apply("fly", "ant"));
        assertEquals(1, subject.apply("elephant", "hippo"));
        assertEquals(8, subject.apply("ABC Corporation", "ABC Corp"));
        assertEquals(20, subject.apply("D N H Enterprises Inc", "D & H Enterprises, Inc."));
        assertEquals(24, subject.apply("My Gym Children's Fitness Center", "My Gym. Childrens Fitness"));
        assertEquals(11, subject.apply("PENNSYLVANIA", "PENNCISYLVNIA"));
        assertEquals(1, subject.apply("left", "right"));
        assertEquals(4, subject.apply("leettteft", "ritttght"));
        assertEquals(15, subject.apply("the same string", "the same string"));
    }
}

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
    void apply_emptyStrings_returnsZero() {
        assertEquals(0, subject.apply("", ""));
        assertEquals(0, subject.apply("left", ""));
        assertEquals(0, subject.apply("", "right"));
    }

    @Test
    void apply_noCharactersInCommon_returnsZero() {
        assertEquals(0, subject.apply("fly", "ant"));
    }

    @Test
    void apply_stringsWithCommonSubsequence_returnsLCSLength() {
        assertEquals(3, subject.apply("frog", "fog"));
        assertEquals(1, subject.apply("elephant", "hippo")); // 'h' and 'p' are common but cannot both appear in order
        assertEquals(1, subject.apply("left", "right"));
        assertEquals(4, subject.apply("leettteft", "ritttght"));
    }

    @Test
    void apply_businessNameVariants_returnsLCSLength() {
        assertEquals(8,  subject.apply("ABC Corporation", "ABC Corp"));
        assertEquals(20, subject.apply("D N H Enterprises Inc", "D & H Enterprises, Inc."));
        assertEquals(24, subject.apply("My Gym Children's Fitness Center", "My Gym. Childrens Fitness"));
        assertEquals(11, subject.apply("PENNSYLVANIA", "PENNCISYLVNIA"));
    }

    @Test
    void apply_identicalStrings_returnsFullStringLength() {
        assertEquals(15, subject.apply("the same string", "the same string"));
    }
}

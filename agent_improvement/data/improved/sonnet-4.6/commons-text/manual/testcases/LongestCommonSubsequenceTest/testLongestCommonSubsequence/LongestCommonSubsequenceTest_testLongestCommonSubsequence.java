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
    void emptyInputsReturnEmptySubsequence() {
        assertEquals("", subject.longestCommonSubsequence("", ""));
        assertEquals("", subject.longestCommonSubsequence("left", ""));
        assertEquals("", subject.longestCommonSubsequence("", "right"));
    }

    @Test
    void noCommonCharactersReturnEmptySubsequence() {
        assertEquals("", subject.longestCommonSubsequence("l", "a"));
        assertEquals("", subject.longestCommonSubsequence("left", "a"));
        assertEquals("", subject.longestCommonSubsequence("fly", "ant"));
    }

    @Test
    void extractsCorrectSubsequenceFromOverlappingStrings() {
        assertEquals("fog",  subject.longestCommonSubsequence("frog", "fog"));
        assertEquals("h",    subject.longestCommonSubsequence("elephant", "hippo"));
        assertEquals("t",    subject.longestCommonSubsequence("left", "right"));
        assertEquals("tttt", subject.longestCommonSubsequence("leettteft", "ritttght"));
    }

    @Test
    void identicalStringsReturnThemselves() {
        assertEquals("the same string", subject.longestCommonSubsequence("the same string", "the same string"));
    }

    @Test
    void realWorldCompanyNameMatching() {
        assertEquals("ABC Corp",
                subject.longestCommonSubsequence("ABC Corporation", "ABC Corp"));
        assertEquals("D  H Enterprises Inc",
                subject.longestCommonSubsequence("D N H Enterprises Inc", "D & H Enterprises, Inc."));
        assertEquals("My Gym Childrens Fitness",
                subject.longestCommonSubsequence("My Gym Children's Fitness Center", "My Gym. Childrens Fitness"));
        assertEquals("PENNSYLVNIA",
                subject.longestCommonSubsequence("PENNSYLVANIA", "PENNCISYLVNIA"));
    }
}

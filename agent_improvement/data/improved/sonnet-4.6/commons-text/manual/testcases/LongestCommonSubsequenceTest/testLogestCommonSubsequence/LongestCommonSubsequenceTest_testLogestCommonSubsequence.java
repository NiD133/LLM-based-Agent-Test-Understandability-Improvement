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
        // Empty inputs: any pairing with an empty string yields an empty subsequence
        assertEquals("", subject.logestCommonSubsequence("", ""));
        assertEquals("", subject.logestCommonSubsequence("left", ""));
        assertEquals("", subject.logestCommonSubsequence("", "right"));

        // No characters in common: result is always empty
        assertEquals("", subject.logestCommonSubsequence("l", "a"));
        assertEquals("", subject.logestCommonSubsequence("left", "a"));
        assertEquals("", subject.logestCommonSubsequence("fly", "ant"));

        // Partial subsequence matches
        assertEquals("fog",  subject.logestCommonSubsequence("frog",     "fog"));
        assertEquals("h",    subject.logestCommonSubsequence("elephant", "hippo"));
        assertEquals("t",    subject.logestCommonSubsequence("left",     "right"));

        // Repeated characters: all matching repeated chars are captured in the LCS
        assertEquals("tttt", subject.logestCommonSubsequence("leettteft", "ritttght"));

        // Business name normalization: LCS extracts the shared words/characters across name variants
        assertEquals("ABC Corp",                 subject.logestCommonSubsequence("ABC Corporation",                  "ABC Corp"));
        assertEquals("D  H Enterprises Inc",     subject.logestCommonSubsequence("D N H Enterprises Inc",            "D & H Enterprises, Inc."));
        assertEquals("My Gym Childrens Fitness", subject.logestCommonSubsequence("My Gym Children's Fitness Center", "My Gym. Childrens Fitness"));
        assertEquals("PENNSYLVNIA",              subject.logestCommonSubsequence("PENNSYLVANIA",                     "PENNCISYLVNIA"));

        // Identical strings: the LCS is the full string itself
        assertEquals("the same string", subject.logestCommonSubsequence("the same string", "the same string"));
    }
}

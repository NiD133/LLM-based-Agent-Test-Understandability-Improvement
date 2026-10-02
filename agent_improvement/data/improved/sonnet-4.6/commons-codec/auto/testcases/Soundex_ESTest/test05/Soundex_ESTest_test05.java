package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Soundex_ESTest_test05 extends Soundex_ESTest_scaffolding {

    /**
     * Verifies that the US_ENGLISH_GENEALOGY Soundex instance computes a difference
     * score of 1 between two strings with low phonetic similarity, and that the default
     * max length of a custom-mapped Soundex instance remains 4.
     */
    @Test(timeout = 4000)
    public void test05() throws Throwable {
        // Create a Soundex instance with a custom mapping string
        Soundex customMappingSoundex = new Soundex("org.apache.commons.codec.EncoderException");

        // Use the genealogy variant to compare two phonetically dissimilar strings;
        // US_ENGLISH_GENEALOGY treats vowels, H, and W as silent (ignored) characters
        int differenceScore = customMappingSoundex.US_ENGLISH_GENEALOGY.difference(
                ")1XSFmv!V?i#",
                "org.apache.commons.codec.EncoderException"
        );

        // The default max length for any Soundex instance is 4 (one letter + three digits)
        assertEquals(4, customMappingSoundex.getMaxLength());

        // The two strings share very few phonetic features, resulting in a low difference score
        assertEquals(1, differenceScore);
    }
}

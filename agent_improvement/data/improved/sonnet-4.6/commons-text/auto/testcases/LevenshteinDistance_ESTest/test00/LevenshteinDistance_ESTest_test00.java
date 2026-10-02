package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LevenshteinDistance_ESTest_test00 extends LevenshteinDistance_ESTest_scaffolding {

    /**
     * Verifies that when apply() returns -1 (threshold exceeded), using that result as a
     * threshold in a new LevenshteinDistance throws IllegalArgumentException because -1 is negative.
     *
     * The 8-char null input vs the 14-char target string "d0tT~H04{j;2W=" exceeds threshold 13,
     * so apply() returns -1. Passing -1 as the threshold to the constructor must throw.
     */
    @Test(timeout = 4000)
    public void test00() throws Throwable {
        // An 8-character sequence of null characters wrapped as a CharSequence
        char[] nullChars = new char[8];
        CharBuffer inputBuffer = CharBuffer.wrap(nullChars);

        // With threshold 13, comparing 8 null chars to the 14-char string returns -1
        // because the actual distance exceeds the threshold
        LevenshteinDistance distanceWithThreshold = new LevenshteinDistance(13);
        Integer distanceResult = distanceWithThreshold.apply((CharSequence) inputBuffer, (CharSequence) "d0tT~H04{j;2W=");

        // distanceResult is -1, which is negative — using it as a threshold must throw
        try {
            new LevenshteinDistance(distanceResult);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Threshold must not be negative
            //
            verifyException("org.apache.commons.text.similarity.LevenshteinDistance", e);
        }
    }
}

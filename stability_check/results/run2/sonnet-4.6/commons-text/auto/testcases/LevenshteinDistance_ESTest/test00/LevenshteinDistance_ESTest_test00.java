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
     * Verifies that constructing LevenshteinDistance with a negative threshold throws IllegalArgumentException.
     *
     * Strategy: compute a limited Levenshtein distance where the actual edit distance exceeds the
     * threshold (13), causing apply() to return -1. Then use that -1 value as a threshold for a
     * new LevenshteinDistance instance, which must be rejected as invalid.
     */
    @Test(timeout = 4000)
    public void test00() throws Throwable {
        // Build a CharSequence of 8 null characters ('\0') to act as the left operand
        char[] nullChars = new char[8];
        CharBuffer source = CharBuffer.wrap(nullChars);

        // With threshold=13, comparing 8-char source to 14-char target returns -1
        // because the true edit distance (>13) exceeds the threshold
        LevenshteinDistance distanceWithThreshold = new LevenshteinDistance(13);
        Integer negativeDistance = distanceWithThreshold.apply((CharSequence) source, (CharSequence) "d0tT~H04{j;2W=");

        // Passing -1 as the threshold must raise an IllegalArgumentException
        try {
            new LevenshteinDistance(negativeDistance);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Threshold must not be negative
            //
            verifyException("org.apache.commons.text.similarity.LevenshteinDistance", e);
        }
    }
}

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
     * Verifies that when apply() returns -1 (distance exceeds the threshold),
     * using that negative result as a threshold for a new LevenshteinDistance
     * throws IllegalArgumentException, since negative thresholds are not allowed.
     */
    @Test(timeout = 4000)
    public void test00() throws Throwable {
        // Build an 8-character source sequence of null chars (\0\0\0\0\0\0\0\0)
        char[] nullChars = new char[8];
        CharBuffer sourceBuffer = CharBuffer.wrap(nullChars);

        // With threshold=13, comparing 8 null chars against a 14-char target
        // exceeds the threshold, so apply() returns -1
        int threshold = 13;
        LevenshteinDistance distanceWithThreshold = new LevenshteinDistance(threshold);
        Integer distanceResult = distanceWithThreshold.apply((CharSequence) sourceBuffer, (CharSequence) "d0tT~H04{j;2W=");

        // distanceResult is -1 because the actual distance (14) exceeds the threshold (13).
        // Passing a negative value as a threshold must throw IllegalArgumentException.
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

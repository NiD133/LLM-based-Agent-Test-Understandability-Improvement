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
     * When the two inputs are too dissimilar to match within the configured
     * threshold, {@link LevenshteinDistance#apply} returns -1. Feeding that -1
     * back into the constructor as a new threshold must be rejected, because a
     * threshold may not be negative.
     */
    @Test(timeout = 4000)
    public void thresholdMustNotBeNegative() throws Throwable {
        // A buffer of 8 NUL characters compared against an unrelated 13-char string.
        CharBuffer leftInput = CharBuffer.wrap(new char[8]);
        CharSequence rightInput = "d0tT~H04{j;2W=";

        // With threshold 13 the strings are still beyond reach, so apply() returns -1.
        LevenshteinDistance limitedDistance = new LevenshteinDistance(Integer.valueOf(13));
        Integer distanceOrNoMatch = limitedDistance.apply((CharSequence) leftInput, rightInput);

        // Using that -1 result as a threshold must be rejected.
        try {
            new LevenshteinDistance(distanceOrNoMatch);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Threshold must not be negative
            verifyException("org.apache.commons.text.similarity.LevenshteinDistance", e);
        }
    }
}

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
     * Verifies that a negative distance produced by a thresholded comparison
     * cannot be reused as the threshold of a new {@link LevenshteinDistance}:
     * the constructor rejects negative thresholds with an
     * {@link IllegalArgumentException}.
     */
    @Test(timeout = 4000)
    public void constructorRejectsNegativeThresholdFromExceededDistance() throws Throwable {
        // A comparison whose true distance exceeds the threshold yields -1.
        CharSequence leftInput = CharBuffer.wrap(new char[8]);
        CharSequence rightInput = "d0tT~H04{j;2W=";
        Integer threshold = Integer.valueOf(13);

        LevenshteinDistance thresholdedDistance = new LevenshteinDistance(threshold);
        Integer comparisonResult = thresholdedDistance.apply(leftInput, rightInput);

        // Feeding that (negative) result back as a threshold must be rejected.
        try {
            new LevenshteinDistance(comparisonResult);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Threshold must not be negative
            verifyException("org.apache.commons.text.similarity.LevenshteinDistance", e);
        }
    }
}

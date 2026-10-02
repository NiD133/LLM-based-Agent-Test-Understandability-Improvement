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
     * When the real edit distance between two inputs exceeds the configured
     * threshold, {@link LevenshteinDistance#apply(CharSequence, CharSequence)}
     * returns the sentinel value {@code -1} rather than the actual distance.
     *
     * Feeding that {@code -1} back into the {@link LevenshteinDistance}
     * constructor must then fail, because a threshold may not be negative.
     */
    @Test(timeout = 4000)
    public void distanceExceedingThresholdReturnsMinusOneAndRejectsNegativeThreshold() throws Throwable {
        // Left input: 8 NUL characters; right input: a 14-character string that
        // shares no characters with the left input, so the true Levenshtein
        // distance is 14.
        CharBuffer leftInput = CharBuffer.wrap(new char[8]);
        String rightInput = "d0tT~H04{j;2W=";

        // A threshold of 13 is below the true distance of 14.
        int threshold = 13;
        LevenshteinDistance limitedDistance = new LevenshteinDistance(threshold);

        // Because the distance (14) is greater than the threshold (13),
        // apply() reports the "not within threshold" sentinel value of -1.
        Integer distance = limitedDistance.apply((CharSequence) leftInput, (CharSequence) rightInput);
        assertEquals(Integer.valueOf(-1), distance);

        // A LevenshteinDistance cannot be built with a negative threshold,
        // so reusing the -1 sentinel as a threshold is rejected.
        try {
            new LevenshteinDistance(distance);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Threshold must not be negative
            verifyException("org.apache.commons.text.similarity.LevenshteinDistance", e);
        }
    }
}

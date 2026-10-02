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
public class DamerauLevenshteinDistance_ESTest_test08 extends DamerauLevenshteinDistance_ESTest_scaffolding {

    /**
     * Verifies that applying the distance algorithm with both null SimilarityInput arguments
     * throws an IllegalArgumentException, even when a threshold of zero is set.
     */
    @Test(timeout = 4000)
    public void test08_applyWithBothNullSimilarityInputs_throwsIllegalArgumentException() throws Throwable {
        Integer zeroThreshold = Integer.valueOf(0);
        DamerauLevenshteinDistance distanceWithZeroThreshold = new DamerauLevenshteinDistance(zeroThreshold);

        try {
            distanceWithZeroThreshold.apply((SimilarityInput<Object>) null, (SimilarityInput<Object>) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Left/right inputs must not be null
            //
            verifyException("org.apache.commons.text.similarity.DamerauLevenshteinDistance", e);
        }
    }
}

package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DamerauLevenshteinDistance_ESTest_test08 extends DamerauLevenshteinDistance_ESTest_scaffolding {

    /**
     * Verifies that {@code apply} rejects null inputs by throwing an
     * {@link IllegalArgumentException} ("Left/right inputs must not be null"),
     * even when the distance is created with a threshold.
     */
    @Test(timeout = 4000)
    public void applyWithNullInputsThrowsIllegalArgumentException() throws Throwable {
        DamerauLevenshteinDistance distanceWithZeroThreshold =
                new DamerauLevenshteinDistance(Integer.valueOf(0));

        try {
            distanceWithZeroThreshold.apply((SimilarityInput<Object>) null, (SimilarityInput<Object>) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected: "Left/right inputs must not be null"
            verifyException("org.apache.commons.text.similarity.DamerauLevenshteinDistance", e);
        }
    }
}

package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LevenshteinDistance_ESTest_test14 extends LevenshteinDistance_ESTest_scaffolding {

    /**
     * Verifies that applying the distance to two null inputs is rejected with an
     * IllegalArgumentException, even when a (non-null) threshold is configured.
     */
    @Test(timeout = 4000)
    public void applyWithBothInputsNullThrowsIllegalArgumentException() throws Throwable {
        Integer threshold = Integer.valueOf(1403);
        LevenshteinDistance distanceWithThreshold = new LevenshteinDistance(threshold);

        try {
            distanceWithThreshold.apply((SimilarityInput<Integer>) null, (SimilarityInput<Integer>) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Message: "CharSequences must not be null"
            verifyException("org.apache.commons.text.similarity.LevenshteinDistance", e);
        }
    }
}

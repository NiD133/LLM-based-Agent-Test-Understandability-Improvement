package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LevenshteinDetailedDistance_ESTest_test15 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    /**
     * Applying the distance to two null {@link SimilarityInput} arguments must
     * reject the call with an {@link IllegalArgumentException}, since the inputs
     * "must not be null".
     */
    @Test(timeout = 4000)
    public void applyWithNullInputsThrowsIllegalArgumentException() throws Throwable {
        Integer threshold = 5;
        LevenshteinDetailedDistance distance = new LevenshteinDetailedDistance(threshold);

        try {
            distance.apply((SimilarityInput<Object>) null, (SimilarityInput<Object>) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Message: "CharSequences must not be null"
            verifyException("org.apache.commons.text.similarity.LevenshteinDetailedDistance", e);
        }
    }
}

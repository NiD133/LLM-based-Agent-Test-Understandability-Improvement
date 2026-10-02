package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LevenshteinDetailedDistance_ESTest_test04 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    /**
     * Applying the distance to two null inputs must be rejected, because the
     * algorithm cannot compare CharSequences that do not exist.
     */
    @Test(timeout = 4000)
    public void applyWithBothInputsNullThrowsIllegalArgumentException() throws Throwable {
        LevenshteinDetailedDistance distance = LevenshteinDetailedDistance.getDefaultInstance();

        try {
            distance.apply((SimilarityInput<Object>) null, (SimilarityInput<Object>) null);
            fail("Expecting exception: IllegalArgumentException (CharSequences must not be null)");
        } catch (IllegalArgumentException e) {
            // Expected: the message is "CharSequences must not be null".
            verifyException("org.apache.commons.text.similarity.LevenshteinDetailedDistance", e);
        }
    }
}

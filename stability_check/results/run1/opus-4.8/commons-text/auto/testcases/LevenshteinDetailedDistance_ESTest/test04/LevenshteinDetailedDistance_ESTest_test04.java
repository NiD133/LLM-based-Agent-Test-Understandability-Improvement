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
     * Applying the distance to two null {@link SimilarityInput} arguments must be
     * rejected, because the algorithm requires non-null character sequences.
     */
    @Test(timeout = 4000)
    public void applyWithNullInputsThrowsIllegalArgumentException() throws Throwable {
        LevenshteinDetailedDistance distance = LevenshteinDetailedDistance.getDefaultInstance();
        SimilarityInput<Object> nullLeft = null;
        SimilarityInput<Object> nullRight = null;

        try {
            distance.apply(nullLeft, nullRight);
            fail("Expecting exception: IllegalArgumentException (CharSequences must not be null)");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.text.similarity.LevenshteinDetailedDistance", e);
        }
    }
}

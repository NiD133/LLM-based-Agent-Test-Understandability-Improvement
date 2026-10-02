package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LevenshteinDistance_ESTest_test04 extends LevenshteinDistance_ESTest_scaffolding {

    /**
     * Verifies that applying the distance with two null SimilarityInput
     * arguments is rejected: the algorithm requires non-null inputs and
     * therefore throws an IllegalArgumentException ("CharSequences must not be null").
     */
    @Test(timeout = 4000)
    public void apply_withBothInputsNull_throwsIllegalArgumentException() throws Throwable {
        LevenshteinDistance distance = LevenshteinDistance.getDefaultInstance();

        try {
            distance.apply((SimilarityInput<Object>) null, (SimilarityInput<Object>) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Thrown by LevenshteinDistance because the inputs must not be null.
            verifyException("org.apache.commons.text.similarity.LevenshteinDistance", e);
        }
    }
}

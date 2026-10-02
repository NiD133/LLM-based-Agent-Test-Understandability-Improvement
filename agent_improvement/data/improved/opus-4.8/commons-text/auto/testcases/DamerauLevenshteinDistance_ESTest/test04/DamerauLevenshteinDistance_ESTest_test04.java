package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DamerauLevenshteinDistance_ESTest_test04 extends DamerauLevenshteinDistance_ESTest_scaffolding {

    /**
     * Calling {@code apply} with two null {@link SimilarityInput} arguments must reject the
     * inputs with an {@link IllegalArgumentException} stating that they must not be null.
     */
    @Test(timeout = 4000)
    public void applyWithNullSimilarityInputsThrowsIllegalArgumentException() throws Throwable {
        DamerauLevenshteinDistance distance = new DamerauLevenshteinDistance();

        try {
            distance.apply((SimilarityInput<Object>) null, (SimilarityInput<Object>) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Message thrown by the algorithm: "Left/right inputs must not be null"
            verifyException("org.apache.commons.text.similarity.DamerauLevenshteinDistance", e);
        }
    }
}

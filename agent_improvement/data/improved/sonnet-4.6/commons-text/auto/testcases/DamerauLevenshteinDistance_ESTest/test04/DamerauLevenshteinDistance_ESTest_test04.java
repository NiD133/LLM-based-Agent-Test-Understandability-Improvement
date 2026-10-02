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
     * Verifies that passing null for both left and right inputs throws an
     * IllegalArgumentException with the message "Left/right inputs must not be null".
     */
    @Test(timeout = 4000)
    public void test04_applyWithBothNullInputs_throwsIllegalArgumentException() throws Throwable {
        DamerauLevenshteinDistance distance = new DamerauLevenshteinDistance();

        try {
            distance.apply((SimilarityInput<Object>) null, (SimilarityInput<Object>) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.text.similarity.DamerauLevenshteinDistance", e);
        }
    }
}

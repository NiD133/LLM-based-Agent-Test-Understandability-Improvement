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

    @Test(timeout = 4000)
    public void test14() throws Throwable {
        // A distance instance with a threshold set (limited mode)
        LevenshteinDistance distance = new LevenshteinDistance(1403);

        // Passing null inputs must throw IllegalArgumentException regardless of threshold
        try {
            distance.apply((SimilarityInput<Integer>) null, (SimilarityInput<Integer>) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // CharSequences must not be null
            //
            verifyException("org.apache.commons.text.similarity.LevenshteinDistance", e);
        }
    }
}

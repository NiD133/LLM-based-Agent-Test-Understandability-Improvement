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
public class LevenshteinDistance_ESTest_test14 extends LevenshteinDistance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test14() throws Throwable {
        Integer integer0 = new Integer(1403);
        LevenshteinDistance levenshteinDistance0 = new LevenshteinDistance(integer0);
        // Undeclared exception!
        try {
            levenshteinDistance0.apply((SimilarityInput<Integer>) null, (SimilarityInput<Integer>) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // CharSequences must not be null
            //
            verifyException("org.apache.commons.text.similarity.LevenshteinDistance", e);
        }
    }
}

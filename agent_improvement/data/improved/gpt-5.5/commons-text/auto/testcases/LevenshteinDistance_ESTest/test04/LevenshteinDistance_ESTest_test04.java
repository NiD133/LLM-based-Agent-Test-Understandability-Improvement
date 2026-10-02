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
public class LevenshteinDistance_ESTest_test04 extends LevenshteinDistance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        LevenshteinDistance defaultDistance = LevenshteinDistance.getDefaultInstance();

        try {
            defaultDistance.apply((SimilarityInput<Object>) null, (SimilarityInput<Object>) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException exception) {
            // Both inputs are null, so LevenshteinDistance should reject the call.
            verifyException("org.apache.commons.text.similarity.LevenshteinDistance", exception);
        }
    }
}

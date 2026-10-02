package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LevenshteinDetailedDistance_ESTest_test15 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test15() throws Throwable {
        Integer threshold = new Integer(5);
        LevenshteinDetailedDistance distanceWithThreshold = new LevenshteinDetailedDistance(threshold);

        try {
            distanceWithThreshold.apply((SimilarityInput<Object>) null, (SimilarityInput<Object>) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // CharSequences must not be null
            //
            verifyException("org.apache.commons.text.similarity.LevenshteinDetailedDistance", e);
        }
    }
}

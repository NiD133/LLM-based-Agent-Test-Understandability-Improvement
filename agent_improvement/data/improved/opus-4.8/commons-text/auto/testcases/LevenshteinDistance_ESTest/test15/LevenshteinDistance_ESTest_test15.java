package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LevenshteinDistance_ESTest_test15 extends LevenshteinDistance_ESTest_scaffolding {

    /**
     * Verifies that the threshold supplied to the constructor is the exact value
     * returned by {@link LevenshteinDistance#getThreshold()}.
     */
    @Test(timeout = 4000)
    public void getThreshold_returnsThresholdGivenToConstructor() throws Throwable {
        final Integer threshold = Integer.valueOf(1403);

        LevenshteinDistance distance = new LevenshteinDistance(threshold);

        assertEquals(1403, (int) distance.getThreshold());
    }
}

package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LevenshteinDetailedDistance_ESTest_test22 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    /**
     * The default instance is created without a threshold, so it should use the
     * unlimited algorithm and report a {@code null} threshold.
     */
    @Test(timeout = 4000)
    public void defaultInstanceHasNoThreshold() throws Throwable {
        LevenshteinDetailedDistance defaultInstance = LevenshteinDetailedDistance.getDefaultInstance();

        Integer threshold = defaultInstance.getThreshold();

        assertNull(threshold);
    }
}

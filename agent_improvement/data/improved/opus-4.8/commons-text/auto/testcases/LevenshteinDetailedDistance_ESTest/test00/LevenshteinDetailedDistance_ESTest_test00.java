package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LevenshteinDetailedDistance_ESTest_test00 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    /**
     * The constructor rejects a negative threshold by throwing an
     * IllegalArgumentException with the message "Threshold must not be negative".
     */
    @Test(timeout = 4000)
    public void constructorRejectsNegativeThreshold() throws Throwable {
        Integer negativeThreshold = Integer.valueOf(-2571);

        try {
            new LevenshteinDetailedDistance(negativeThreshold);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Threshold must not be negative
            verifyException("org.apache.commons.text.similarity.LevenshteinDetailedDistance", e);
        }
    }
}

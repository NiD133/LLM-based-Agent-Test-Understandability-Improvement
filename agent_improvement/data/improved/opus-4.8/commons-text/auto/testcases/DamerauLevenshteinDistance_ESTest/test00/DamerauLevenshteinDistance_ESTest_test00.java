package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DamerauLevenshteinDistance_ESTest_test00 extends DamerauLevenshteinDistance_ESTest_scaffolding {

    /**
     * The constructor must reject a negative threshold by throwing an
     * IllegalArgumentException ("Threshold must not be negative").
     */
    @Test(timeout = 4000)
    public void constructorWithNegativeThresholdThrowsIllegalArgumentException() throws Throwable {
        Integer negativeThreshold = Integer.valueOf(-17);

        try {
            new DamerauLevenshteinDistance(negativeThreshold);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Threshold must not be negative
            verifyException("org.apache.commons.text.similarity.DamerauLevenshteinDistance", e);
        }
    }
}

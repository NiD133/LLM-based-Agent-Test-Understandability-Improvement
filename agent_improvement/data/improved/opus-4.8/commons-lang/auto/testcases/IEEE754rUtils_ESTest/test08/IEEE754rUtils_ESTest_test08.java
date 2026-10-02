package org.apache.commons.lang3.math;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IEEE754rUtils_ESTest_test08 extends IEEE754rUtils_ESTest_scaffolding {

    /** Tolerance for comparing float results. */
    private static final float DELTA = 0.01F;

    /**
     * min(float, float, float) should return the smallest of the three
     * arguments, here the zero that sits between two equal larger values.
     */
    @Test(timeout = 4000)
    public void minOfThreeFloatsReturnsSmallest() throws Throwable {
        float smaller = 0.0F;
        float larger = 1488.587F;

        float actualMin = IEEE754rUtils.min(larger, smaller, larger);

        assertEquals(smaller, actualMin, DELTA);
    }
}

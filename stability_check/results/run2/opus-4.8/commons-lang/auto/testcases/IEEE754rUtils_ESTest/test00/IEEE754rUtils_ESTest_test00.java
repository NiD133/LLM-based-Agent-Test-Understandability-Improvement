package org.apache.commons.lang3.math;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IEEE754rUtils_ESTest_test00 extends IEEE754rUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link IEEE754rUtils#min(float...)} returns the minimum
     * of a float array. For an array of three default-initialized floats
     * (all 0.0F), the minimum is 0.0F.
     */
    @Test(timeout = 4000)
    public void minOfAllZeroFloatArrayReturnsZero() throws Throwable {
        float[] allZeros = new float[3];

        float actualMin = IEEE754rUtils.min(allZeros);

        assertEquals(0.0F, actualMin, 0.01F);
    }
}

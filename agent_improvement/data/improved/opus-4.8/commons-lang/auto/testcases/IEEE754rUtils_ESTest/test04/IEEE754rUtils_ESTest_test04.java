package org.apache.commons.lang3.math;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IEEE754rUtils_ESTest_test04 extends IEEE754rUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link IEEE754rUtils#max(float...)} returns the maximum
     * element of a float array. A new float[3] is zero-initialised, so every
     * element is 0.0F and the maximum is therefore 0.0F.
     */
    @Test(timeout = 4000)
    public void maxOfAllZeroFloatArrayReturnsZero() throws Throwable {
        float[] allZeroFloats = new float[3];

        float maximum = IEEE754rUtils.max(allZeroFloats);

        assertEquals(0.0F, maximum, 0.01F);
    }
}

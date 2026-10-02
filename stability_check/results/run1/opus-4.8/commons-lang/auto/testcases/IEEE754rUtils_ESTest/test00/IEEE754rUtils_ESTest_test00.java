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
     * A float array is default-initialized to all zeros, so the minimum
     * element returned by {@link IEEE754rUtils#min(float...)} must be 0.0F.
     */
    @Test(timeout = 4000)
    public void minOfAllZeroFloatArrayReturnsZero() throws Throwable {
        float[] allZeros = new float[3];

        float minimum = IEEE754rUtils.min(allZeros);

        assertEquals(0.0F, minimum, 0.01F);
    }
}

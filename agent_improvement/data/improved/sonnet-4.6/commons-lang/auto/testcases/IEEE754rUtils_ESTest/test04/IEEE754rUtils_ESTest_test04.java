package org.apache.commons.lang3.math;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IEEE754rUtils_ESTest_test04 extends IEEE754rUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04_maxOfAllZeroFloatArray_returnsZero() throws Throwable {
        // An array of 3 floats initialized to their default value (0.0f)
        float[] allZeros = new float[3];

        float result = IEEE754rUtils.max(allZeros);

        // The maximum of [0.0, 0.0, 0.0] is 0.0
        assertEquals(0.0F, result, 0.01F);
    }
}

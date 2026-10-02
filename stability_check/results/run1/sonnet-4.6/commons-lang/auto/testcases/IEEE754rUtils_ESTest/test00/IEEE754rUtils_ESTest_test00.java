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

    @Test(timeout = 4000)
    public void test00_minOfAllZerosReturnsZero() throws Throwable {
        // A float array initialized to default values (all 0.0f)
        float[] allZeros = new float[3];

        float result = IEEE754rUtils.min(allZeros);

        // The minimum of [0.0, 0.0, 0.0] should be 0.0
        assertEquals(0.0F, result, 0.01F);
    }
}

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

    // IEEE754rUtils.min(float[]) returns the smallest element in the array,
    // ignoring NaN values per IEEE-754r semantics.
    @Test(timeout = 4000)
    public void test00_minOfAllZerosReturnsZero() throws Throwable {
        float[] allZeros = new float[3]; // default-initialised: {0.0F, 0.0F, 0.0F}
        float result = IEEE754rUtils.min(allZeros);
        assertEquals(0.0F, result, 0.01F);
    }
}

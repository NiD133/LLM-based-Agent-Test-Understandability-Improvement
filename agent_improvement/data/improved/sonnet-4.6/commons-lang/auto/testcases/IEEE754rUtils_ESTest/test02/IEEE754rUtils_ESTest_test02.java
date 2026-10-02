package org.apache.commons.lang3.math;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IEEE754rUtils_ESTest_test02 extends IEEE754rUtils_ESTest_scaffolding {

    /**
     * Verifies that IEEE754rUtils.min() returns 0.0 when all elements in the
     * array are default-initialized doubles (i.e., 0.0).
     */
    @Test(timeout = 4000)
    public void test02_minOfZeroInitializedArray_returnsZero() throws Throwable {
        // A two-element array whose values are both 0.0 (Java default for double)
        double[] allZeros = new double[2];

        double result = IEEE754rUtils.min(allZeros);

        assertEquals("min of all-zero array should be 0.0", 0.0, result, 0.01);
    }
}

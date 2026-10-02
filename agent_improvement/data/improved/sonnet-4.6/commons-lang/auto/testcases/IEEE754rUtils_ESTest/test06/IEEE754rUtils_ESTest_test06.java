package org.apache.commons.lang3.math;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IEEE754rUtils_ESTest_test06 extends IEEE754rUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06_maxOfZeroInitializedArrayReturnsZero() throws Throwable {
        // A 2-element double array whose values default to 0.0
        double[] zeros = new double[2];

        double result = IEEE754rUtils.max(zeros);

        // max of {0.0, 0.0} should be 0.0
        assertEquals(0.0, result, 0.01);
    }
}

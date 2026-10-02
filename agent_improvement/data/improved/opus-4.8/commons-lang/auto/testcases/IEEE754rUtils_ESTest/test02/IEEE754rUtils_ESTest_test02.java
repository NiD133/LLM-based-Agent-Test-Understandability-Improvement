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
     * Verifies that {@link IEEE754rUtils#min(double...)} returns the smallest
     * element of the array. When every element is the default value 0.0, the
     * minimum is also 0.0.
     */
    @Test(timeout = 4000)
    public void minOfAllZeroDoubleArrayReturnsZero() throws Throwable {
        double[] allZeros = new double[2];

        double minimum = IEEE754rUtils.min(allZeros);

        assertEquals(0.0, minimum, 0.01);
    }
}

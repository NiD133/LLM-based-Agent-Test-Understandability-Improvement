package org.apache.commons.lang3.math;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IEEE754rUtils_ESTest_test06 extends IEEE754rUtils_ESTest_scaffolding {

    private static final double DELTA = 0.01;

    /**
     * The maximum of an array whose elements are all zero is itself zero.
     * A {@code new double[2]} is initialised to {0.0, 0.0} by default.
     */
    @Test(timeout = 4000)
    public void maxOfAllZeroArrayIsZero() throws Throwable {
        double[] allZeros = new double[2];

        double max = IEEE754rUtils.max(allZeros);

        assertEquals(0.0, max, DELTA);
    }
}

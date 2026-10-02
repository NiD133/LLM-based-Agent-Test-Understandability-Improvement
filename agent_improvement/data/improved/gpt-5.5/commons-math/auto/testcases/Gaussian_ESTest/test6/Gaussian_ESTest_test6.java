package org.apache.commons.math4.legacy.analysis.function;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.apache.commons.math4.legacy.analysis.differentiation.DerivativeStructure;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Gaussian_ESTest_test6 extends Gaussian_ESTest_scaffolding {

    private static final double NORMALIZATION_FACTOR = 1193.809784545181;
    private static final double INVALID_SIGMA = 0.0;
    private static final String GAUSSIAN_CLASS_NAME =
            "org.apache.commons.math4.legacy.analysis.function.Gaussian";

    @Test(timeout = 4000)
    public void test6() throws Throwable {
        try {
            new Gaussian(NORMALIZATION_FACTOR, INVALID_SIGMA);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            //
            // 0 is smaller than, or equal to, the minimum (0)
            //
            verifyException(GAUSSIAN_CLASS_NAME, e);
        }
    }
}

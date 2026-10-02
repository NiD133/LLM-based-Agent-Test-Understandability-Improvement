package org.apache.commons.math4.legacy.analysis.interpolation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LoessInterpolator_ESTest_test10 extends LoessInterpolator_ESTest_scaffolding {

    private static final int INVALID_BANDWIDTH = 2;
    private static final int ROBUSTNESS_ITERATIONS = 2;
    private static final double ACCURACY = 1.0E-12;
    private static final String LOESS_INTERPOLATOR_CLASS =
            "org.apache.commons.math4.legacy.analysis.interpolation.LoessInterpolator";

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        try {
            new LoessInterpolator(INVALID_BANDWIDTH, ROBUSTNESS_ITERATIONS, ACCURACY);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            //
            // 2 out of [0, 1] range: bandwidth (2)
            //
            verifyException(LOESS_INTERPOLATOR_CLASS, e);
        }
    }
}
